package nota;


public class ClassNota {
    private String title;
    private String conteudo;

    public ClassNota(String title, String conteudo) {
        this.title = title;
        this.conteudo = conteudo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    } 
}
