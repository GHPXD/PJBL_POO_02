//-------@autors-----------//
//Guilherme Henrique Pereira//
//Léo Gustavo Del Ré//

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class Pet {
    private String name;
    private String breed;
    private String birthdate;
    private int age;
    private int id; // Variável de instância para o ID

    public Pet(String name, String breed, String birthdate, int age) {
        this.name = name;
        this.breed = breed;
        this.birthdate = birthdate;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public String getBreed() {
        return breed;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public int getAge() {
        return age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static int calculateAge(String birthdate) {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse(birthdate, formatter);
        Period period = Period.between(date, today);
        return period.getYears();
    }

    @Override
    public String toString() {
        return "ID: " + id + "\n" +
                "Nome: " + name + "\n" +
                "Raça: " + breed + "\n" +
                "Data de Nascimento: " + birthdate + "\n" +
                "Idade: " + age + " anos";
    }
}
