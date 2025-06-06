package ar.com.manager.inventory.dto;

import java.util.List;

public class SaleDto {
    private static final long serialVersionUID = 1L;
    private Integer id;

    private String saleDate;
    private double totalPrice;
    private Integer userId;
    private Integer clientId;
    private List<SaleDetailDto> saleDetail;
    private boolean deleted;

    public SaleDto() {
    }

    public SaleDto(Integer id, String saleDate, double totalPrice, Integer userId, Integer clientId, List<SaleDetailDto> saleDetail) {
        this.id = id;
        this.saleDate = saleDate;
        this.totalPrice = totalPrice;
        this.userId = userId;
        this.clientId = clientId;
        this.saleDetail = saleDetail;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(String saleDate) {
        this.saleDate = saleDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public List<SaleDetailDto> getSaleDetail() {
        return saleDetail;
    }

    public void setSaleDetail(List<SaleDetailDto> saleDetail) {
        this.saleDetail = saleDetail;
    }

    public void addSaleDetail(SaleDetailDto saleDetail) {
        this.saleDetail.add(saleDetail);
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
