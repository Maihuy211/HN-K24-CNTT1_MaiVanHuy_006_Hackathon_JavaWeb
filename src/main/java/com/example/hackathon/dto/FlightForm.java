package com.example.hackathon.dto;

import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

public class FlightForm {
    private Long id;
    @NotBlank(message ="Không đc để trống")
    @Size(min = 5, max = 20, message = "Mã chuyến bay từ 5 đến 20 ký tự")
    private String flightNumber;
    private String departureTime;
    @NotBlank(message ="Không đc để trống")
    private String destination;

    @NotNull(message = "Giá vé không được để trống")
    @Min(value = 100, message = "Giá vé phải lớn hơn hoặc bằng 100")
    @Max(value = 10000, message = "Giá vé phải nhỏ hơn hoặc bằng 10000")
    private double ticketPrice;

    private MultipartFile airlineLogo;

    public FlightForm() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public MultipartFile getAirlineLogo() {
        return airlineLogo;
    }

    public void setAirlineLogo(MultipartFile airlineLogo) {
        this.airlineLogo = airlineLogo;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }
}
