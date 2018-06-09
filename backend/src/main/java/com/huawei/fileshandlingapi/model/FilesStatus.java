package com.huawei.fileshandlingapi.model;

import java.util.Map;

import static com.huawei.fileshandlingapi.constants.CooperadoresConstants.*;

public class FilesStatus {
    private boolean detailview;
    private boolean sicte;
    private boolean dico;
    private boolean conectar;
    private boolean applus;
    private boolean enecon;
    private boolean fscr;

    public FilesStatus() {
    }

    public FilesStatus(Map<String, Boolean> statusMap, boolean hasDV) {
        if (hasDV) {
            detailview = statusMap.get("detailview");
        }

        sicte = statusMap.get(SICTE_KEY);
        dico = statusMap.get(DICO_KEY);
        enecon = statusMap.get(ENECON_KEY);
        applus = statusMap.get(APPLUS_KEY);
        conectar = statusMap.get(CONECTAR_KEY);
        fscr = statusMap.get(FSCR_KEY);
    }

    public boolean isDetailview() {
        return detailview;
    }

    public void setDetailview(boolean detailview) {
        this.detailview = detailview;
    }

    public boolean isSicte() {
        return sicte;
    }

    public void setSicte(boolean sicte) {
        this.sicte = sicte;
    }

    public boolean isDico() {
        return dico;
    }

    public void setDico(boolean dico) {
        this.dico = dico;
    }

    public boolean isConectar() {
        return conectar;
    }

    public void setConectar(boolean conectar) {
        this.conectar = conectar;
    }

    public boolean isApplus() {
        return applus;
    }

    public void setApplus(boolean applus) {
        this.applus = applus;
    }

    public boolean isEnecon() {
        return enecon;
    }

    public void setEnecon(boolean enecon) {
        this.enecon = enecon;
    }

    public boolean isFscr() {
        return fscr;
    }

    public void setFscr(boolean fscr) {
        this.fscr = fscr;
    }
}
