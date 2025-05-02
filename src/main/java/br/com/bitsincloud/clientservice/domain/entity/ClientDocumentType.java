package br.com.bitsincloud.clientservice.domain.entity;

public enum ClientDocumentType {
    RG("RG"),
    CNH("CNH"),
    FOTO("FOTO"),
    CPF("CPF"),
    CNPJ("CNPJ"),
    PASSAPORTE("PASSAPORTE");

    private String name;

    ClientDocumentType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static ClientDocumentType findByName(String name) {
        for (ClientDocumentType type : ClientDocumentType.values()) {
            if (type.getName().equalsIgnoreCase(name)) {
                return type;
            }
        }
        return null;
    }
}
