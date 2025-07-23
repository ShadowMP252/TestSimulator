
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class Generator {
    public String question;
    public String[] answers;
    public int correctAnswer;

    public Generator() {}

    public Generator(String question, String[] answers, int correctAnswer) {
        this.question = question;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
    
    }

    public static List<Generator> loadFlashcardsFromJson(String filePath) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(Paths.get(filePath).toFile(), new TypeReference<List<Generator>>() {});
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    public void shuffleAnswers() {
        List<String> shuffled = new ArrayList<>(List.of(this.answers)); // convert array to list
        Collections.shuffle(shuffled);

        // Get the text of the original correct answer
        String correctAnswerText = this.answers[this.correctAnswer - 1];

        // Convert back to array and set shuffled answers
        this.answers = shuffled.toArray(new String[0]);

        // Update correctAnswer to new index (+1 because answers are 1-indexed)
        this.correctAnswer = shuffled.indexOf(correctAnswerText) + 1;
    }
}

