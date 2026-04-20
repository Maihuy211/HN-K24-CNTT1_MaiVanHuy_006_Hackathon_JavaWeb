package com.example.hackathon.service;

import com.example.hackathon.model.Flight;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlightService {
    private final List<Flight> flights = new ArrayList<>(
            List.of(
                    new Flight(1L, "VJ-123", "Hà Nội", "2026-04-20", 1200000.0, "vj-logo.png"),
                    new Flight(2L, "VN-234", "Đà Nẵng", "2026-04-21", 1500000.0, "vn-logo.png"),
                    new Flight(3L, "QH-345", "TP.HCM", "2026-04-22", 1800000.0, "qh-logo.png")
            )
    );

    public List<Flight> getAll() {
        return flights;
    }

    public Flight findById(Long id) {
        return flights.stream()
                .filter(f -> f.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void add(Flight f) {
        flights.add(f);
    }

    public void update(Long id, Flight updated) {
        for (int i = 0; i < flights.size(); i++) {
            if (flights.get(i).getId().equals(id)) {
                flights.set(i, updated);
                return;
            }
        }
    }

    public void deleteFlight(Long id) {
        flights.removeIf(f -> f.getId().equals(id));
    }

    public boolean existsById(Long id) {
        if (id == null) return false;
        return flights.stream()
                .filter(f -> f.getId() != null)
                .anyMatch(f -> f.getId().equals(id));
    }

    public List<Flight> search(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()){
            return flights;
        }
        String key = keyword.toLowerCase();
        return flights.stream()
                .filter(f ->
                        f.getFlightNumber().toLowerCase().contains(key) ||
                                f.getDestination().toLowerCase().contains(key)
                )
                .collect(Collectors.toList());
    }

    private final String uploadDir = "C:\\Users\\Admin\\Documents\\code\\code\\JAVA\\Java web\\JavaWebApplication_Session11\\Hackathon\\src\\main\\webapp\\uploads\\";
    public String uploadFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return null;
        }
        try {
            String fileName = System.currentTimeMillis() +  file.getOriginalFilename();
            File dest = new File(uploadDir, fileName);
            file.transferTo(dest);
            return fileName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}