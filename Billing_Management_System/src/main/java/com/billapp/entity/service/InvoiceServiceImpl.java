package com.billapp.entity.service;



import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.billapp.entity.Invoice;

import com.billapp.repository.InvoiceRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.ByteArrayOutputStream;


@Service
public class InvoiceServiceImpl implements InvoiceService{

    @Autowired
    private InvoiceRepository invoiceRepository;
    
  
	@Override
	public void saveInvoice(Invoice invoice) {
		invoiceRepository.save(invoice);
		}

	@Override
	public Invoice findById(Long id) {
	
		return invoiceRepository.findById(id).get();
	}

	@Override
	public List<Invoice> getAllInvoices() 
	{
		 return invoiceRepository.findAll();
	}

	@Override
	public void deleteInvoice(Long id) {
		
		invoiceRepository.deleteById(id);
	}

	
	@Override
	public ByteArrayInputStream generateBillingReportPDF(List<Invoice> invoices) 
	{
		 Document document = new Document();
		    ByteArrayOutputStream out = new ByteArrayOutputStream();

		    try {
		        PdfWriter.getInstance(document, out);
		        document.open();

		        // Title
		        document.add(new Paragraph("Billing Report", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16)));

		        // Table
		        PdfPTable table = new PdfPTable(4);
		        table.setWidthPercentage(100);
		        table.setWidths(new int[]{4, 3, 3, 3});

		        // Table Header
		        Stream.of("Invoice Number", "Client Name", "Date", "Total Amount")
		              .forEach(header -> {
		                  PdfPCell cell = new PdfPCell();
		                  cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
		                  cell.setPhrase(new Phrase(header, FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
		                  table.addCell(cell);
		              });

		        // Table Data
		        for (Invoice invoice : invoices) {
		            table.addCell(invoice.getInvoiceNumber());
		            table.addCell(invoice.getClientName());
		            table.addCell(invoice.getDate().toString());
		            table.addCell(String.valueOf(invoice.getTotalAmount()));
		        }

		        document.add(table);
		        document.close();
		    } catch (Exception e) {
		        e.printStackTrace();
		    }

		    return new ByteArrayInputStream(out.toByteArray());
	}

	@Override
	public List<Invoice> getInvoicesByFilters(LocalDate startDate, LocalDate endDate, String clientName,
			String productName) {
		return invoiceRepository.findInvoicesByFilters(startDate, endDate, clientName, productName);
	}

	@Override
	public List<Invoice> getInvoicesByClientName(String clientName) {
		  return invoiceRepository.findByClientName(clientName);
	}

	@Override
	public ByteArrayInputStream generateClientReportPDF(List<Invoice> invoices) 
	{
		Document document = new Document();
	    ByteArrayOutputStream out = new ByteArrayOutputStream();

	    try {
	        PdfWriter.getInstance(document, out);
	        document.open();

	        // Title
	        document.add(new Paragraph("Client Report", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16)));

	        // Table
	        PdfPTable table = new PdfPTable(4);
	        table.setWidthPercentage(100);
	        table.setWidths(new int[]{4, 3, 3, 3});

	        // Table Header
	        Stream.of("Invoice Number", "Client Name", "Date", "Total Amount")
	              .forEach(header -> {
	                  PdfPCell cell = new PdfPCell();
	                  cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	                  cell.setPhrase(new Phrase(header, FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
	                  table.addCell(cell);
	              });

	        // Table Data
	        for (Invoice invoice : invoices) 
	        {
	            table.addCell(invoice.getInvoiceNumber());
	            table.addCell(invoice.getClientName());
	            table.addCell(invoice.getDate().toString());
	            table.addCell(String.valueOf(invoice.getTotalAmount()));
	        }

	        document.add(table);
	        document.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return new ByteArrayInputStream(out.toByteArray());
	}


	
}
