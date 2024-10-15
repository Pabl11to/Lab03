package laboratorio3;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;

public class Laboratorio3 {

    private JLabel timeLabel; // Para mostrar el tiempo
    private Timer stopwatchTimer; // Temporizador para el cronómetro
    private Timer alarmTimer; // Temporizador para la alarma
    private long startTime; // Tiempo de inicio del cronómetro
    private long alarmTime; // Tiempo para la alarma
    private boolean alarmSet = false; // Flag para verificar si la alarma está configurada

    public static void main(String[] args) {
        // Ejecutar la creación de la GUI en el hilo de despacho de eventos
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Laboratorio3().createAndShowGUI();
            }
        });
    }

    private void createAndShowGUI() {
        // Crear el marco (ventana)
        JFrame frame = new JFrame("Cronómetro con Alarma");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLayout(new FlowLayout());

        // Crear la etiqueta para mostrar el tiempo
        timeLabel = new JLabel("00:00:00");
        frame.add(timeLabel);

        // Botón para iniciar el cronómetro
        JButton startButton = new JButton("Iniciar Cronómetro");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                start(); // cuando inicia, solo nombre
            }
        });
        frame.add(startButton);

        // Botón para configurar la alarma
        JButton setAlarmButton = new JButton("Configurar Alarma");
        setAlarmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                configalarm();//cuando se configure la alarma
            }
        });
        frame.add(setAlarmButton);

        // Hacer visible el marco
        frame.setVisible(true);
    }
    private void start() {
        // Inicializar el tiempo de inicio
        startTime = System.currentTimeMillis();

        // Crear un temporizador para el cronómetro que actualiza cada segundo
        stopwatchTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Time();
            }
        });
        stopwatchTimer.start();
    }

    private void Time() {
        long elapsedTime = System.currentTimeMillis() - startTime;
        // Convertir milisegundos a formato HH:mm:ss
        String formattedTime = String.format(" %02d:%02d:%02d",
            TimeUnit.MILLISECONDS.toHours(elapsedTime),
            TimeUnit.MILLISECONDS.toMinutes(elapsedTime) % 60,
            TimeUnit.MILLISECONDS.toSeconds(elapsedTime) % 60);
        timeLabel.setText(formattedTime);
        
        if (alarmSet && System.currentTimeMillis() >= alarmTime) {
            // Si el tiempo actual supera el tiempo de la alarma, iniciar el temporizador de la alarma
            if (alarmTimer == null) {
                alarmTimer = new Timer(10000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        alarm();
                    }
                });
                alarmTimer.start();
            }
        }
    }
    private void configalarm() {
        String input = JOptionPane.showInputDialog("Configura la alarma en minutos:");
        try {
            int minutes = Integer.parseInt(input);
            alarmTime = System.currentTimeMillis() + (minutes * 6000);
            alarmSet = true;
            JOptionPane.showMessageDialog(null, "Alarma configurada para " + minutes + " minutos.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Por favor, introduce un número válido.");
        }
    }

    private void alarm() {
        JOptionPane.showMessageDialog(null, "¡Tiempo de alarma!");
    }
}
