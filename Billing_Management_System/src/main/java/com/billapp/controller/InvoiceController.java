package com.billapp.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.io.OutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.billapp.entity.Invoice;
import com.billapp.entity.Product;
import com.billapp.entity.PurchasedProduct;
import com.billapp.entity.service.InvoiceService;
import com.billapp.entity.service.ProductService;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;



@Controller
public class InvoiceController {

	@Autowired
	private ProductService productService;

	@Autowired
	private InvoiceService invoiceService;

	@GetMapping("/new")
	public String createInvoiceForm(Model model) {
		model.addAttribute("products", productService.getAllProducts());
		model.addAttribute("invoice", new Invoice());
		return "invoice-form";
	}
	
	
	@PostMapping("/create")
	public String createInvoice(@ModelAttribute Invoice invoice, @RequestParam("productIds") List<Long> productIds,
			@RequestParam("quantities") List<Integer> quantities, Model model) {

		if (productIds == null || productIds.isEmpty()) {
			model.addAttribute("error", "No products selected.");
			return "invoice-form";
		}

		double totalAmount = 0.0;
		List<PurchasedProduct> purchasedProducts = new ArrayList<>();

		for (int i = 0; i < productIds.size(); i++) {
			Long productId = productIds.get(i);
			int quantity = quantities.get(i);

			Product product = productService.getProductById(productId);

			if (product == null) {
				model.addAttribute("error", "Invalid product selected.");
				return "invoice-form";
			}

			if (quantity <= 0 || product.getStockQuantity() < quantity) {
				model.addAttribute("error", "Insufficient Stock for product: " + product.getName() + " "+ "Available Stock is:"+ product.getStockQuantity());
				return "invoice-form";
			}

			// Reduce stock and create PurchasedProduct
			product.setStockQuantity(product.getStockQuantity() - quantity);
			productService.saveProduct(product);

			PurchasedProduct purchasedProduct = new PurchasedProduct();
			purchasedProduct.setProductId(product.getId());
			purchasedProduct.setName(product.getName());
			purchasedProduct.setQuantity(quantity);
			purchasedProduct.setPrice(product.getPrice());
			purchasedProduct.setTotalPrice(product.getPrice() * quantity);
			purchasedProducts.add(purchasedProduct);

			totalAmount += purchasedProduct.getTotalPrice();
		}

		invoice.setInvoiceNumber("INV-" + System.currentTimeMillis());
		invoice.setDate(LocalDate.now());
		invoice.setTotalAmount(totalAmount);
		invoice.setPurchasedProducts(purchasedProducts);

		invoiceService.saveInvoice(invoice);
		model.addAttribute("invoice", invoice);
		return "invoice-details";
	}

	
	@GetMapping("/invoices")
	public String listInvoices(Model model) {

		List<Invoice> invoices = invoiceService.getAllInvoices();
		model.addAttribute("invoices", invoices);
		return "invoice-list";
	}

	@GetMapping("/invoice/details/{id}")
	public String viewInvoiceDetails(@PathVariable Long id, Model model) {

		Invoice invoice = invoiceService.findById(id);

		if (invoice == null) {

			model.addAttribute("error", "Invoice not found");
			return "error";
		}

		model.addAttribute("invoice", invoice);

		return "invoice-details";
	}

	@GetMapping("/invoice/download/{id}")
	public void downloadInvoice(@PathVariable Long id, HttpServletResponse response) throws IOException {
		Invoice invoice = invoiceService.findById(id);
		if (invoice == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			response.getWriter().write("Invoice not found.");
			return;
		}

		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition",
				"attachment; filename=invoice-" + invoice.getInvoiceNumber() + ".pdf");

		try (OutputStream out = response.getOutputStream()) {
			Document document = new Document();
			PdfWriter.getInstance(document, out);

			document.open();
			document.add(new Paragraph("Invoice Number: " + invoice.getInvoiceNumber()));
			document.add(new Paragraph("Client Name: " + invoice.getClientName()));
			document.add(new Paragraph("Date: " + invoice.getDate()));
			document.add(new Paragraph("Total Amount:" + invoice.getTotalAmount()));

			document.add(new Paragraph("\nPurchased Products:"));
			PdfPTable table = new PdfPTable(4);
			table.setWidthPercentage(100);
			table.addCell("Product Name");
			table.addCell("Quantity");
			table.addCell("Price");
			table.addCell("Total Price");

			for (PurchasedProduct item : invoice.getPurchasedProducts()) {
				table.addCell(item.getName());
				table.addCell(String.valueOf(item.getQuantity()));
				table.addCell(String.valueOf(item.getPrice()));
				table.addCell(String.valueOf(item.getTotalPrice()));
			}

			document.add(table);
			document.close();
		} catch (DocumentException e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().write("Error generating invoice: " + e.getMessage());
		}
	}

	@GetMapping("/invoice/delete/{id}")
	public String deleteInvoice(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		invoiceService.deleteInvoice(id);
		redirectAttributes.addFlashAttribute("msg", "Deleted successfully");
		return "redirect:/invoices";
	}

	
	@GetMapping("/billing-report-filters")
	public String showBillingReportFilters() 
	{
	    return "billing-report-filters";
	}
	
	@GetMapping("/billing-report")
	public String showBillingReport(
	    @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
	    @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
	    @RequestParam(required = false) String clientName,
	    @RequestParam(required = false) String productName,
	    Model model) {

	    List<Invoice> filteredInvoices = invoiceService.getInvoicesByFilters(startDate, endDate, clientName, productName);
	    model.addAttribute("invoices", filteredInvoices);
	    return "billing-report";
	}

	
	 @GetMapping("/billing-report/download")
	 public ResponseEntity<byte[]> downloadBillingReportPDF(
	         @RequestParam(required = false) LocalDate startDate,
	         @RequestParam(required = false) LocalDate endDate,
	         @RequestParam(required = false) String clientName,
	         @RequestParam(required = false) String productName) {
	     List<Invoice> invoices = invoiceService.getInvoicesByFilters(startDate, endDate, clientName, productName);
	     ByteArrayInputStream pdf = invoiceService.generateBillingReportPDF(invoices);

	     HttpHeaders headers = new HttpHeaders();
	     headers.add("Content-Disposition", "inline; filename=billing_report.pdf");

	     return ResponseEntity.ok()
	             .headers(headers)
	             .contentType(MediaType.APPLICATION_PDF)
	             .body(pdf.readAllBytes());
	 }
	 
 
		@GetMapping("/client-report-filters")
		public String showClientReportFilters() 
		{
		    return "client-report-filters";
		}
	
		
		@GetMapping("/client-report")
		public String showClientReport(
		    @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
		    @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
		    @RequestParam(required = false) String clientName,
		    @RequestParam(required = false) String productName,
		    Model model) {

		    List<Invoice> filteredInvoices = invoiceService.getInvoicesByFilters(startDate, endDate, clientName, productName);
		    model.addAttribute("invoices", filteredInvoices);
		    return "client-report";
		}
		

		 @GetMapping("/client-report/download")
		 public ResponseEntity<byte[]> downloadClientReportPDF(
		         @RequestParam(required = false) LocalDate startDate,
		         @RequestParam(required = false) LocalDate endDate,
		         @RequestParam(required = false) String clientName,
		         @RequestParam(required = false) String productName) 
		 {
			 
		     List<Invoice> invoices = invoiceService.getInvoicesByFilters(startDate, endDate, clientName, productName);
		     ByteArrayInputStream pdf = invoiceService.generateClientReportPDF(invoices);

		     HttpHeaders headers = new HttpHeaders();
		     headers.add("Content-Disposition", "inline; filename=client_report.pdf");

		     return ResponseEntity.ok()
		             .headers(headers)
		             .contentType(MediaType.APPLICATION_PDF)
		             .body(pdf.readAllBytes());
		 }
				
}
