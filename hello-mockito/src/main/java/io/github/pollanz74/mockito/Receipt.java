package io.github.pollanz74.mockito;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Receipt {

    private String companyName;
    private Double netImport;
    private Double vat;
    private Double total;

    @Override
    public String toString() {
        return "\n" +
                "\t\t" + "[\n" +
                "\t\t\t" + "companyName: " + companyName + '\n' +
                "\t\t\t" + "netImport: " + netImport + '\n' +
                "\t\t\t" + "vat: " + vat + '\n' +
                "\t\t\t" + "total: " + total + '\n' +
                "\t\t" + "]\n";
    }
}
