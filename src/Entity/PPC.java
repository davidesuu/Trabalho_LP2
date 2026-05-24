package Entity;

public class PPC {
    private Long id;
    private Integer cursoId;
    private Integer anoVigencia;
    private Integer cargaHorariaTotal;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCursoId() {
        return cursoId;
    }

    public void setCursoId(Integer cursoId) {
        this.cursoId = cursoId;
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
