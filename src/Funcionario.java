// Importações necessárias
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;

// Definição da classe Funcionario
public class Funcionario extends Pessoa {
    // Atributos da classe Funcionario
    private BigDecimal salario;
    private String funcao;

    // Construtor da classe Funcionario
    public Funcionario(String nome, LocalDate dataNascimento, BigDecimal salario, String funcao) {
        super(nome, dataNascimento);
        this.salario = salario;
        this.funcao = funcao;
    }

    // Métodos getters e setters para os atributos
    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    // Método para formatar o salário conforme especificação
    public String formatarSalario() {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        return df.format(salario);
    }
}
