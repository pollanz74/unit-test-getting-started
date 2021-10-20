package io.github.pollanz74.mockito;

public class Receipt {

    private String companyName;
    private Double netImport;
    private Double vat;
    private Double total;

    public Receipt()  {}

    public Receipt(String companyName, Double importPayed, Double vat, Double total) {
        this.companyName = companyName;
        this.netImport = importPayed;
        this.vat = vat;
        this.total = total;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Double getNetImport() {
        return netImport;
    }

    public void setNetImport(Double netImport) {
        this.netImport = netImport;
    }

    public Double getVat() {
        return vat;
    }

    public void setVat(Double vat) {
        this.vat = vat;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

}
