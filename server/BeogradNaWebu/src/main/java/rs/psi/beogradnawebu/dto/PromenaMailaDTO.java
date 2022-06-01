package rs.psi.beogradnawebu.dto;

import rs.psi.beogradnawebu.annotations.PripadaEmail;
import rs.psi.beogradnawebu.annotations.ValidanEmail;

public class PromenaMailaDTO {
    @PripadaEmail
    private String stariEmail;

    @ValidanEmail
    private String noviEmail;

    public String getStariEmail() {
        return stariEmail;
    }

    public void setStariEmail(String stariEmail) {
        this.stariEmail = stariEmail;
    }

    public String getNoviEmail() {
        return noviEmail;
    }

    public void setNoviEmail(String noviEmail) {
        this.noviEmail = noviEmail;
    }
}
