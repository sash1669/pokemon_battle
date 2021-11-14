package game;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import monster.Monster;
import monster.Move;
import monster.Trainer;

/**
 * Various utility functions to generate random moves, monsters, and trainers.
 * 
 * @author Mary Ellen Foster <MaryEllen.Foster@glasgow.ac.uk>
 */
public class Utils {

	/** Randomness */
	public static final Random RAND = new Random();
	/** Possible types */
	private static final List<String> TYPES = Arrays.asList("Normal", "Fire", "Water", "Electric", "Grass", null, null,
			null, null, null);

	/**
	 * Generates and returns a random 10-character string with the first character capitalised.
	 * Based on code from https://www.baeldung.com/java-random-string
	 * 
	 * @return A random string
	 */
	private static String getRandomString() {
		int leftLimit = (int)'a';
		int rightLimit = (int)'z';
		int targetStringLength = 10;

		String result = RAND.ints(leftLimit, rightLimit + 1).limit(targetStringLength)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
		return result.substring(0, 1).toUpperCase() + result.substring(1);
	}

	/**
	 * Returns a random valid type, optionally including null.
	 * 
	 * @param includeNull If true, the result could also be null (with 50% probability)
	 * @return A random valid type, or null
	 */
	public static String getRandomType(boolean includeNull) {
		if (includeNull) {
			return TYPES.get(RAND.nextInt(TYPES.size()));
		} else {
			return TYPES.get(RAND.nextInt(TYPES.size() - 5));
		}
	}

	/**
	 * Generates and returns a random move, with random name, type, and power.
	 * 
	 * @return A random move
	 */
	public static Move getRandomMove() {
		String type = getRandomType(true);
		if (type == null) {
			return null;
		}
		return new Move(getRandomString(), type, RAND.nextInt(181));
	}

	/**
	 * Generates and returns a random monster, with a random name and a random set of moves.
	 * 
	 * @return A random monster
	 * @see #getRandomMove()
	 */
	public static Monster getRandomMonster() {
		String name = getRandomString();
		String type1 = getRandomType(false);
		String type2 = getRandomType(true);
		int hp = RAND.nextInt(200);

		Monster m;
		if (type2 == null || type2.equals(type1)) {
			m = new Monster(name, type1, hp);
		} else {
			m = new Monster(name, type1, type2, hp);
		}

		for (int i = 0; i < 4; i++) {
			m.setMove(i, getRandomMove());
		}

		return m;
	}


	/**
	 * Generates and returns a random Trainer, with up to 5 random monsters.
	 * 
	 * @return A random trainer
	 * @see #getRandomTrainer()
	 */
	public static Trainer getRandomTrainer() {
		Trainer t = new Trainer(getRandomString());
		for (int j = 0; j < RAND.nextInt(5) + 1; j++) {
			t.addMonster(getRandomMonster());
		}
		return t;
	}

}
