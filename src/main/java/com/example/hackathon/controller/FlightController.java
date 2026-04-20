package com.example.hackathon.controller;

import com.example.hackathon.dto.FlightForm;
import com.example.hackathon.model.Flight;
import com.example.hackathon.service.FlightService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class FlightController {

    @Autowired
    private FlightService flightService;

    @GetMapping("/flights")
    public String list(@RequestParam(value = "keyword", required = false) String keyword,
                       Model model) {
        model.addAttribute("flights", flightService.search(keyword));
        model.addAttribute("keyword", keyword);
        return "flights";
    }

    @GetMapping("/flights/create")
    public String showCreate(Model model) {
        model.addAttribute("flightForm", new FlightForm());
        return "create-form";
    }

    @PostMapping("/flights/create")
    public String create(@Valid @ModelAttribute("flightForm") FlightForm form,
                         BindingResult result,
                         Model model) {
        if (result.hasErrors()) {
            return "create-form";
        }
        if (flightService.existsById(form.getId())) {
            model.addAttribute("error", "ID đã tồn tại!");
            return "create-form";
        }
        String fileName = flightService.uploadFile(form.getAirlineLogo());
        Flight f = new Flight(
                form.getId(),
                form.getFlightNumber(),
                form.getDestination(),
                form.getDepartureTime(),
                form.getTicketPrice(),
                fileName
        );
        flightService.add(f);
        return "redirect:/flights";
    }

    @GetMapping("/flights/edit/{id}")
    public String showEdit(@PathVariable("id") Long id, Model model) {

        Flight f = flightService.findById(id);
        if (f == null) {
            return "redirect:/flights";
        }
        FlightForm form = new FlightForm();
        form.setId(f.getId());
        form.setFlightNumber(f.getFlightNumber());
        form.setDestination(f.getDestination());
        form.setDepartureTime(f.getDepartureTime());
        form.setTicketPrice(f.getTicketPrice());

        model.addAttribute("flightForm", form);
        model.addAttribute("oldLogo", f.getAirlineLogo());
        return "edit-form";
    }

    @PostMapping("/flights/edit/{id}")
    public String update(@PathVariable("id") Long id,
                         @Valid @ModelAttribute("flightForm") FlightForm form,
                         BindingResult result) {
        if (result.hasErrors()) {
            return "edit-form";
        }
        Flight old = flightService.findById(id);
        String fileName = "";
        if (old != null) {
            fileName = old.getAirlineLogo();
        }
        if (form.getAirlineLogo() != null && !form.getAirlineLogo().isEmpty()) {
            String uploadedFile = flightService.uploadFile(form.getAirlineLogo());
            if (uploadedFile != null) {
                fileName = uploadedFile;
            }
        }
        Flight updated = new Flight(
                id,
                form.getFlightNumber(),
                form.getDestination(),
                form.getDepartureTime(),
                form.getTicketPrice(),
                fileName
        );
        flightService.update(id, updated);
        return "redirect:/flights";
    }

    @PostMapping("/flights/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        flightService.deleteFlight(id);
        return "redirect:/flights";
    }
}