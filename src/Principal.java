import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe principal para executar as ações solicitadas no teste prático.
 */
public class Principal {

    /**
     * Método principal para executar as ações.
     *
     * @param args Argumentos de linha de comando.
     */
    public static void main(String[] args) {
        List<Funcionario> funcionarios = new ArrayList<>();
        inserirFuncionarios(funcionarios);
        removerFuncionario(funcionarios, "João");

        System.out.println("Funcionários:");
        for (Funcionario funcionario : funcionarios) {
            System.out.println(funcionario);
        }

        aumentarSalario(funcionarios, BigDecimal.TEN);
        Map<String, List<Funcionario>> funcionariosPorFuncao = agruparPorFuncao(funcionarios);

        System.out.println("\nFuncionários agrupados por função:");
        for (Map.Entry<String, List<Funcionario>> entry : funcionariosPorFuncao.entrySet()) {
            System.out.println("Função: " + entry.getKey());
            for (Funcionario funcionario : entry.getValue()) {
                System.out.println("- " + funcionario.getNome());
            }
        }

        imprimirAniversariantes(funcionarios, 10, 12);
        Funcionario maisVelho = encontrarFuncionarioMaisVelho(funcionarios);
        System.out.println("\nFuncionário mais velho:");
        System.out.println("Nome: " + maisVelho.getNome());
        System.out.println("Idade: " + maisVelho.calcularIdade() + " anos");

        System.out.println("\nFuncionários em ordem alfabética:");
        funcionarios.sort((f1, f2) -> f1.getNome().compareTo(f2.getNome()));
        for (Funcionario funcionario : funcionarios) {
            System.out.println("- " + funcionario.getNome());
        }

        BigDecimal totalSalarios = calcularTotalSalarios(funcionarios);
        System.out.println("\nTotal dos salários dos funcionários: R$ " + totalSalarios.setScale(2, RoundingMode.HALF_UP).toString().replace(".", ","));

        BigDecimal salarioMinimo = new BigDecimal("1212.00");
        System.out.println("\nSalários mínimos ganhos por cada funcionário:");
        for (Funcionario funcionario : funcionarios) {
            BigDecimal qtdSalariosMinimos = funcionario.getSalario().divide(salarioMinimo, 2, RoundingMode.DOWN);
            System.out.println(funcionario.getNome() + ": " + qtdSalariosMinimos.setScale(2, RoundingMode.HALF_UP));
        }
    }

    /**
     * Insere todos os funcionários na lista fornecida.
     *
     * @param funcionarios Lista de funcionários.
     */
    private static void inserirFuncionarios(List<Funcionario> funcionarios) {
        funcionarios.add(new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"));
        funcionarios.add(new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador"));
        funcionarios.add(new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Coordenador"));
        funcionarios.add(new Funcionario("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Recepcionista"));
        funcionarios.add(new Funcionario("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Contador"));
        funcionarios.add(new Funcionario("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Diretor"));
        funcionarios.add(new Funcionario("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente"));
        funcionarios.add(new Funcionario("Heloísa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista"));
        funcionarios.add(new Funcionario("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente"));
    }

    /**
     * Remove um funcionário da lista pelo nome.
     *
     * @param funcionarios Lista de funcionários.
     * @param nome Nome do funcionário a ser removido.
     */
    private static void removerFuncionario(List<Funcionario> funcionarios, String nome) {
        funcionarios.removeIf(funcionario -> funcionario.getNome().equals(nome));
    }

    /**
     * Aumenta o salário de todos os funcionários em um percentual especificado.
     *
     * @param funcionarios Lista de funcionários.
     * @param percentualAumento Percentual de aumento (ex: 10 para 10%).
     */
    private static void aumentarSalario(List<Funcionario> funcionarios, BigDecimal percentualAumento) {
        for (Funcionario funcionario : funcionarios) {
            BigDecimal aumento = funcionario.getSalario().multiply(percentualAumento).divide(new BigDecimal("100"));
            funcionario.setSalario(funcionario.getSalario().add(aumento));
        }
    }

    /**
     * Agrupa os funcionários por função.
     *
     * @param funcionarios Lista de funcionários.
     * @return Mapa com a função como chave e a lista de funcionários como valor.
     */
    private static Map<String, List<Funcionario>> agruparPorFuncao(List<Funcionario> funcionarios) {
        Map<String, List<Funcionario>> funcionariosPorFuncao = new HashMap<>();
        for (Funcionario funcionario : funcionarios) {
            funcionariosPorFuncao.computeIfAbsent(funcionario.getFuncao(), k -> new ArrayList<>()).add(funcionario);
        }
        return funcionariosPorFuncao;
    }

    /**
     * Imprime os funcionários que fazem aniversário nos meses especificados.
     *
     * @param funcionarios Lista de funcionários.
     * @param meses Meses dos aniversariantes.
     */
    private static void imprimirAniversariantes(List<Funcionario> funcionarios, int... meses) {
        System.out.println("\nFuncionários que fazem aniversário em outubro e dezembro:");
        for (Funcionario funcionario : funcionarios) {
            int mesAniversario = funcionario.getDataNascimento().getMonthValue();
            for (int mes : meses) {
                if (mesAniversario == mes) {
                    System.out.println("- " + funcionario.getNome());
                }
            }
        }
    }

    /**
     * Encontra o funcionário mais velho.
     *
     * @param funcionarios Lista de funcionários.
     * @return Funcionário mais velho.
     */
    private static Funcionario encontrarFuncionarioMaisVelho(List<Funcionario> funcionarios) {
        return funcionarios.stream()
                .min((f1, f2) -> f1.getDataNascimento().compareTo(f2.getDataNascimento()))
                .orElseThrow();
    }

    /**
     * Calcula o total dos salários dos funcionários.
     *
     * @param funcionarios Lista de funcionários.
     * @return Total dos salários.
     */
    private static BigDecimal calcularTotalSalarios(List<Funcionario> funcionarios) {
        BigDecimal total = BigDecimal.ZERO;
        for (Funcionario funcionario : funcionarios) {
            total = total.add(funcionario.getSalario());
        }
        return total;
    }
}
