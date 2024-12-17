package com.billapp.entity.service;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.List;

import com.billapp.entity.Invoice;

public interface InvoiceService {

	public void saveInvoice(Invoice invoice);

	public Invoice findById(Long id);

	public List<Invoice> getAllInvoices();

	public void deleteInvoice(Long id);

	public List<Invoice> getInvoicesByFilters(LocalDate startDate, LocalDate endDate, String clientName,
			String productName);

	public List<Invoice> getInvoicesByClientName(String clientName);

	public ByteArrayInputStream generateBillingReportPDF(List<Invoice> invoices);

	public ByteArrayInputStream generateClientReportPDF(List<Invoice> invoices);

}
