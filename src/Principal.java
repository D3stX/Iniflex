import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Principal {

    public static void main(String[] args) {
        // Inserir todos os funcionários
        List<Funcionario> funcionarios = new ArrayList<>();
        inserirFuncionarios(funcionarios);

        // Remover o funcionário "João"
        removerFuncionario(funcionarios, "João");

        // Imprimir todos os funcionários com todas suas informações
        System.out.println("Funcionários:");
        for (Funcionario funcionario : funcionarios) {
            System.out.println(funcionario);
        }

        // Aumentar salário em 10%
        aumentarSalario(funcionarios, BigDecimal.TEN);

        // Agrupar os funcionários por função
        Map<String, List<Funcionario>> funcionariosPorFuncao = agruparPorFuncao(funcionarios);

        // Imprimir os funcionários agrupados por função
        System.out.println("\nFuncionários agrupados por função:");
        for (Map.Entry<String, List<Funcionario>> entry : funcionariosPorFuncao.entrySet()) {
            System.out.println("Função: " + entry.getKey());
            for (Funcionario funcionario : entry.getValue()) {
                System.out.println("- " + funcionario.getNome());
            }
        }

        // Imprimir os funcionários que fazem aniversário nos meses 10 e 12
        imprimirAniversariantes(funcionarios, 10, 12);

        // Imprimir o funcionário mais velho
        Funcionario maisVelho = encontrarFuncionarioMaisVelho(funcionarios);
        System.out.println("\nFuncionário mais velho:");
        System.out.println("Nome: " + maisVelho.getNome());
        System.out.println("Idade: " + maisVelho.calcularIdade() + " anos");

        // Imprimir os funcionários em ordem alfabética
        System.out.println("\nFuncionários em ordem alfabética:");
        funcionarios.sort((f1, f2) -> f1.getNome().compareTo(f2.getNome()));
        for (Funcionario funcionario : funcionarios) {
            System.out.println("- " + funcionario.getNome());
        }

        // Imprimir o total dos salários dos funcionários
        BigDecimal totalSalarios = calcularTotalSalarios(funcionarios);
        System.out.println("\nTotal dos salários dos funcionários: R$ " + totalSalarios.setScale(2, RoundingMode.HALF_UP));

        // Imprimir quantos salários mínimos cada funcionário ganha
        BigDecimal salarioMinimo = new BigDecimal("1212.00");
        System.out.println("\nSalários mínimos ganhos por cada funcionário:");
        for (Funcionario funcionario : funcionarios) {
            BigDecimal qtdSalariosMinimos = funcionario.getSalario().divide(salarioMinimo, 2, RoundingMode.DOWN);
            System.out.println(funcionario.getNome() + ": " + qtdSalariosMinimos.setScale(2, RoundingMode.HALF_UP));
        }
    }

    // Métodos auxiliares

    private static void inserirFuncionarios(List<Funcionario> funcionarios) {
        funcionarios.add(new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"));
        funcionarios.add(new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Caio"));
        funcionarios.add(new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Operador Coordenador"));
        funcionarios.add(new Funcionario("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Heitor"));
        funcionarios.add(new Funcionario("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Arthur"));
        funcionarios.add(new Funcionario("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Diretor"));
        funcionarios.add(new Funcionario("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente"));
        funcionarios.add(new Funcionario("Heloísa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista"));
        funcionarios.add(new Funcionario("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente"));
    }

    private static void removerFuncionario(List<Funcionario> funcionarios, String nome) {
        funcionarios.removeIf(funcionario -> funcionario.getNome().equals(nome));
    }

    private static void aumentarSalario(List<Funcionario> funcionarios, BigDecimal percentualAumento) {
        for (Funcionario funcionario : funcionarios) {
            BigDecimal aumento = funcionario.getSalario().multiply(percentualAumento.divide(BigDecimal.valueOf(100)));
            funcionario.setSalario(funcionario.getSalario().add(aumento));
        }
    }

    private static Map<String, List<Funcionario>> agruparPorFuncao(List<Funcionario> funcionarios) {
        Map<String, List<Funcionario>> funcionariosPorFuncao = new HashMap<>();
        for (Funcionario funcionario : funcionarios) {
            String funcao = funcionario.getFuncao();
            if (!funcionariosPorFuncao.containsKey(funcao)) {
                funcionariosPorFuncao.put(funcao, new ArrayList<>());
            }
            funcionariosPorFuncao.get(funcao).add(funcionario);
        }
        return funcionariosPorFuncao;
    }

    private static void imprimirAniversariantes(List<Funcionario> funcionarios, int mes1, int mes2) {
        System.out.println("\nFuncionários que fazem aniversário em outubro e dezembro:");
        for (Funcionario funcionario : funcionarios) {
            int mesAniversario = funcionario.getDataNascimento().getMonthValue();
            if (mesAniversario == mes1 || mesAniversario == mes2) {
                System.out.println("- " + funcionario.getNome());
            }
        }
    }

    private static Funcionario encontrarFuncionarioMaisVelho(List<Funcionario> funcionarios) {
        Funcionario maisVelho = null;
        int idadeMaisVelho = Integer.MIN_VALUE;

        for (Funcionario funcionario : funcionarios) {
            int idade = funcionario.calcularIdade();
            if (idade > idadeMaisVelho) {
                idadeMaisVelho = idade;
                maisVelho = funcionario;
            }
        }

        return maisVelho;
    }

    private static BigDecimal calcularTotalSalarios(List<Funcionario> funcionarios) {
        BigDecimal totalSalarios = BigDecimal.ZERO;
        for (Funcionario funcionario : funcionarios) {
            totalSalarios = totalSalarios.add(funcionario.getSalario());
        }
        return totalSalarios;
    }

    // Classe Funcionario

    private static class Funcionario {
        private String nome;
        private LocalDate dataNascimento;
        private BigDecimal salario;
        private String funcao;

        public Funcionario(String nome, LocalDate dataNascimento, BigDecimal salario, String funcao) {
            this.nome = nome;
            this.dataNascimento = dataNascimento;
            this.salario = salario;
            this.funcao = funcao;
        }

        public String getNome() {
            return nome;
        }

        public LocalDate getDataNascimento() {
            return dataNascimento;
        }

        public BigDecimal getSalario() {
            return salario;
        }

        public void setSalario(BigDecimal salario) {
            this.salario = salario;
        }

        public String getFuncao() {
            return funcao;
        }

        public int calcularIdade() {
            return LocalDate.now().getYear() - dataNascimento.getYear();
        }

        @Override
        public String toString() {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return "Nome: " + nome +
                    "\nData de Nascimento: " + dataNascimento.format(formatter) +
                    "\nSalário: R$ " + salario.setScale(2, RoundingMode.HALF_UP).toString().replace(".", ",") +
                    "\nFunção: " + funcao + "\n";
        }
    }
}
