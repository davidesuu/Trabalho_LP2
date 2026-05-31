package Entity;

public class PPC {
    private Long id;
    private Long cursoId;
    private Integer anoVigencia;
    private Integer cargaHorariaTotal;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCursoId(Long cursoId) {
        this.cursoId = cursoId;
    }

    public Long getCursoId() {
        return cursoId;
    }

    public Integer getAnoVigencia() {
        return anoVigencia;
    }

    public void setAnoVigencia(Integer anoVigencia) {
        this.anoVigencia = anoVigencia;
    }

    public Integer getCargaHorariaTotal() {
        return cargaHorariaTotal;
    }

    public void setCargaHorariaTotal(Integer cargaHorariaTotal) {
        this.cargaHorariaTotal = cargaHorariaTotal;
    }
}
