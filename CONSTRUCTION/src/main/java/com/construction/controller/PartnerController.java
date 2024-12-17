package com.construction.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.construction.model.Partner;
import com.construction.service.PartnerService;

@Controller
public class PartnerController {

    @Autowired
    private PartnerService partnerService;

    @GetMapping("/add-partner")
    public String addPartnerForm() {
        return "add_partner"; 
    }

    @PostMapping("/partners/save")
    public String savePartner(@RequestParam("logo") MultipartFile logo, RedirectAttributes redirectAttributes) {
        try {
            Partner partner = new Partner();
            partner.setLogo(logo.getBytes());
            partner.setLogoType(logo.getContentType());
            partnerService.savePartner(partner);

            redirectAttributes.addFlashAttribute("msg", "Partner added successfully!");
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("msg", "Failed to upload partner logo.");
        }
        return "redirect:/add-partner";
    }

    @GetMapping("/partners/list")
    public String getPartnerList(Model model) {
        List<Partner> partners = partnerService.getAllPartners();
        model.addAttribute("partners", partners);
        return "partnerList";
    }

    @PostMapping("/partners/delete/{id}")
    public String deletePartner(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        partnerService.deletePartnerById(id);
        redirectAttributes.addFlashAttribute("msg", "Partner deleted successfully!");
        return "redirect:/partners/list";
    }

    @GetMapping("/partner/logo/{id}")
    @ResponseBody
    public ResponseEntity<byte[]> getPartnerLogo(@PathVariable Long id) {
        Partner partner = partnerService.getPartnerById(id);
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_TYPE, partner.getLogoType())
            .body(partner.getLogo());
    }


}
