package common;
/**
 * SMTP implementation based on code by R�al Gagnon mailto:real@rgagnon.com
 */

import models.Bowler;
import models.Score;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Objects;
import java.util.Vector;

public class ScoreReport {

    private String content;

    public ScoreReport(Bowler bowler, int[] scores, int games) {
        String nick = bowler.getNickName();
        String full = bowler.getFullName();
        Vector<Score> v = null;
        try {
            v = ScoreHistoryFile.getScores(nick);
        } catch (Exception e) {
            System.err.println("Error: " + e);
        }

        content = "";
        content += "--Lucky Strike Bowling Alley Score Report--\n";
        content += "\n";
        content += "Report for " + full + ", aka \"" + nick + "\":\n";
        content += "\n";
        content += "Final scores for this session: ";
        content += scores[0];
        StringBuilder temp = new StringBuilder();
        for (int i = 1; i < games; i++)
            temp.append(", ").append(scores[i]);
        //content += ", " + scores[i];
        content += temp;
        temp = new StringBuilder();
        content += ".\n";
        content += "\n";
        content += "\n";
        content += "Previous scores by date: \n";
        for (Score score : Objects.requireNonNull(v)) {
            temp.append("  ").append(score.getDate()).append(" - ").append(score.getScore()).append("\n");
            //content += "  " + score.getDate() + " - " + score.getScore();
            //content += "\n";
        }
        content += temp;
        content += "\n\n";
        content += "Thank you for your continuing patronage.";
    }

    public void sendEmail(String recipient) {
        try {
            Socket s = new Socket("osfmail.rit.edu", 25);
            BufferedReader in =
                    new BufferedReader(
                            new InputStreamReader(s.getInputStream(), "8859_1"));
            BufferedWriter out =
                    new BufferedWriter(
                            new OutputStreamWriter(s.getOutputStream(), "8859_1"));

            //String boundary = "DataSeparatorString";

            // here you are supposed to send your username
            sendln(out, "HELLO world");
            sendln(out, "MAIL FROM: <mda2376@rit.edu>");
            sendln(out, "RCPT TO: <" + recipient + ">");
            sendln(out, "DATA");
            sendln(out, "Subject: Bowling Score Report ");
            sendln(out, "From: <Lucky Strikes Bowling Club>");

            sendln(out, "Content-Type: text/plain; charset=\"us-ascii\"\r\n");
            sendln(out, content + "\n\n");
            sendln(out, "\r\n");

            sendln(out, ".");
            sendln(out, "QUIT");
            s.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendPrintout() {
        PrinterJob job = PrinterJob.getPrinterJob();
        PrintableText printobj = new PrintableText(content);
        job.setPrintable(printobj);

        if (job.printDialog()) {
            try {
                job.print();
            } catch (PrinterException e) {
                System.err.println("ScoreReport error message " + e);
            }
        }
    }

    // private void sendln(BufferedReader in, BufferedWriter out, String s) {
    //     try {
    //         out.write(s + "\r\n");
    //         out.flush();
    //         // System.out.println(s);
    //         s = in.readLine();
    //         // System.out.println(s);
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    // }

    private void sendln(BufferedWriter out, String s) {
        try {
            out.write(s + "\r\n");
            out.flush();
            System.out.println(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}