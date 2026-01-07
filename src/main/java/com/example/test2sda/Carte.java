package com.example.test2sda;

public class Carte {
    private String titlu;
    private String autor;
    private int an;
    private String gen;
    private boolean imprumutata=false;
    private int zile;
    private Student student;
    public Carte(String titlu, String autor, int an, String gen) {
        this.titlu = titlu;
        this.autor=autor;
        this.an = an;
        this.gen = gen;
        this.imprumutata=false;
        this.zile=0;
        this.student=null;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getTitlu() {
        return titlu;
    }

    public String getAutor() {
        return autor;
    }

    public int getAn() {
        return an;
    }

    public String getGen() {
        return gen;
    }

    public boolean isImprumutata() {
        return imprumutata;
    }

    public void setImprumutata(boolean imprumutata) {
        this.imprumutata = imprumutata;
    }

    public int getZile() {
        return zile;
    }

    public void setZile(int zile) {
        this.zile = zile;
    }

    @Override
    public String toString() {
        if(imprumutata)
            return "\n"+titlu + " de " + autor + ", din anul " +an+ ", genul: " + gen
                + ", imprumutata: "+imprumutata+" de atatea zile: "+zile+" de catre "
                    + student;
        else
            return "\n"+ titlu + " de " + autor + ", din anul " +an+ ", genul: " + gen
                    + ", disponibila";
    }
}