import cats.effect.ContextShift;

public class Start {
    private static ContextShift ContextShift$;

    public static void main(String[] args) {
        new TestConsul().saveString("1", "first text");
        new TestConsul().saveString("2", "second text");
        new TestConsul().getStringByID("1");

    }
}
