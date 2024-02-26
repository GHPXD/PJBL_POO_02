import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PetShopInterface extends JFrame {
    private JTextField tfName;
    private JTextField tfBreed;
    private JTextField tfBirthdate;
    private JTextArea taOutput;
    private ArrayList<Pet> pets;
    private int idCounter; // Contador para gerar IDs únicos de cada pet cadastrado

    public PetShopInterface() {
        pets = new ArrayList<>();
        idCounter = 1; // Inicializa o contador de IDs em 1

        setTitle("Cadastro de Pets");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(600, 500)); // Ajusta tamanho da janela

        // Criação do butões do menu
        JButton btnRegister = new JButton("Cadastrar");
        JButton btnPrint = new JButton("Imprimir");
        JButton btnSearch = new JButton("Buscar");
        JButton btnDelete = new JButton("Excluir");
        JButton btnExit = new JButton("Encerrar");
        taOutput = new JTextArea(15, 30); // Aumenta o número de linhas da caixa de texto
        JScrollPane scrollPane = new JScrollPane(taOutput);

        JPanel buttonPanel = new JPanel(new GridLayout(5, 1, 5, 5));
        buttonPanel.add(btnRegister);
        buttonPanel.add(btnPrint);
        buttonPanel.add(btnSearch);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnExit);

        JPanel outputPanel = new JPanel(new BorderLayout());
        outputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Adiciona espaçamento da caixa de texto do menu

        outputPanel.add(scrollPane);

        Container container = getContentPane();
        container.setLayout(new BorderLayout());
        container.add(buttonPanel, BorderLayout.WEST);
        container.add(outputPanel, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);

        // Listener dos botões
        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showRegistrationFields();
            }
        });

        btnPrint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                printPets();
            }
        });

        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchPet();
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deletePet();
            }
        });

        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    private void showRegistrationFields() {
        JPanel registrationPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(5, 5, 5, 5);

        JLabel lblName = new JLabel("Nome:");
        registrationPanel.add(lblName, c);

        c.gridx++;
        tfName = new JTextField(20);
        registrationPanel.add(tfName, c);

        c.gridx = 0;
        c.gridy++;
        JLabel lblBreed = new JLabel("Raça:");
        registrationPanel.add(lblBreed, c);

        c.gridx++;
        tfBreed = new JTextField(20);
        registrationPanel.add(tfBreed, c);

        c.gridx = 0;
        c.gridy++;
        JLabel lblBirthdate = new JLabel("Data de Nascimento:");
        registrationPanel.add(lblBirthdate, c);

        c.gridx++;
        tfBirthdate = new JTextField(10);
        registrationPanel.add(tfBirthdate, c);

        int option = JOptionPane.showConfirmDialog(this, registrationPanel, "Cadastro de Pet", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            registerPet();
        }
    }

    private void registerPet() {
        String name = tfName.getText();
        String breed = tfBreed.getText();
        String birthdate = tfBirthdate.getText();

        if (name.isEmpty() || breed.isEmpty() || birthdate.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos.", "Erro de Cadastro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int age = Pet.calculateAge(birthdate);

        Pet pet = new Pet(name, breed, birthdate, age);
        pet.setId(idCounter); // Atribui o ID gerado ao pet
        pets.add(pet);
        idCounter++; // Incrementa o contador de IDs

        JOptionPane.showMessageDialog(this, "Pet cadastrado com sucesso!", "Cadastro realizado", JOptionPane.INFORMATION_MESSAGE);
        clearFields();
    }

    private void printPets() {
        taOutput.setText("");
        if (pets.isEmpty()) {
            taOutput.setText("Nenhum pet cadastrado.");
        } else {
            for (Pet pet : pets) {
                taOutput.append(pet.toString());
                taOutput.append("\n\n");
            }
        }
    }

    private void searchPet() {
        String input = JOptionPane.showInputDialog(this, "Digite o ID do pet:", "Buscar Pet", JOptionPane.QUESTION_MESSAGE);
        if (input != null && !input.isEmpty()) {
            try {
                int id = Integer.parseInt(input);
                Pet foundPet = null;
                for (Pet pet : pets) {
                    if (pet.getId() == id) {
                        foundPet = pet;
                        break;
                    }
                }

                if (foundPet != null) {
                    taOutput.setText(foundPet.toString());
                } else {
                    taOutput.setText("Nenhum pet encontrado com o ID fornecido.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "ID inválido. Digite um número inteiro.", "Erro de Busca", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deletePet() {
        String input = JOptionPane.showInputDialog(this, "Digite o ID do pet a ser excluído:", "Excluir Pet", JOptionPane.QUESTION_MESSAGE);
        if (input != null && !input.isEmpty()) {
            try {
                int id = Integer.parseInt(input);
                boolean removed = false;
                for (int i = 0; i < pets.size(); i++) {
                    if (pets.get(i).getId() == id) {
                        pets.remove(i);
                        removed = true;
                        break;
                    }
                }

                if (removed) {
                    JOptionPane.showMessageDialog(this, "Pet excluído com sucesso!", "Exclusão realizada", JOptionPane.INFORMATION_MESSAGE);
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(this, "Nenhum pet encontrado com o ID fornecido.", "Erro de Exclusão", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "ID inválido. Digite um número inteiro.", "Erro de Exclusão", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void clearFields() {
        tfName.setText("");
        tfBreed.setText("");
        tfBirthdate.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                PetShopInterface petInterface = new PetShopInterface();
                petInterface.setVisible(true);
            }
        });
    }
}
