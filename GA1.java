import java.util.*;

public class GA1 {
    private int INITIAL_POPULATION_SIZE = 450;
    private int TOURNAMENT_ROUNDS = INITIAL_POPULATION_SIZE / 32;
    private int OPERATIONS = 20000;

    private List<GA1Candidate> mInitialPopulation = new ArrayList<>();
    private List<GA1Candidate> mNextPopulation = new ArrayList<>();

    private GA1Candidate mSolution = null;

    public void start() {
        for (int i = 0 ; i != INITIAL_POPULATION_SIZE ; i++) {
            mInitialPopulation.add(new GA1Candidate());
        }

        for (int i = 0 ; i != OPERATIONS ; i++) {
            GA1Candidate prevWinner = null;
            while (true) {
                GA1Candidate winner = performTournamentSelection(mInitialPopulation);
                if (winner == null) {
                    System.out.println("ERROR : NO TOURNAMENT WINNER !");
                    System.exit(1);
                }
                mInitialPopulation.remove(winner);

                GA1Candidate winnerMutated = performMutation(winner);

                mNextPopulation.add(winnerMutated);

                if (prevWinner != null) {
                    GA1Candidate[] coArray = performCrossover(prevWinner, winner);
                    mNextPopulation.addAll(Arrays.asList(coArray));
                }
                prevWinner = winner;
                if (mNextPopulation.size() > INITIAL_POPULATION_SIZE) {
                    break;
                }
            }
            while (mNextPopulation.size() != INITIAL_POPULATION_SIZE) {
                mNextPopulation.remove(mNextPopulation.size() - 1);
            }

            mInitialPopulation.clear();
            mInitialPopulation.addAll(mNextPopulation);
            mNextPopulation.clear();
        }

        mInitialPopulation.sort(Comparator.comparingDouble(GA1Candidate::getFitness));
       /* System.out.println("=== RESULTS ===");
        for (GA1Candidate cd : mInitialPopulation) {
            cd.printCandidate();
        }*/
        mSolution = mInitialPopulation.get(0);
        /*System.out.println("\n=== SOLUTION ===");
        mSolution.printCandidate();*/
    }

    private GA1Candidate performTournamentSelection(List<GA1Candidate> initialPopulation) {
        List<GA1Candidate> challengerList = new ArrayList<>();

        int i = 0;
        while (i != TOURNAMENT_ROUNDS) {
            GA1Candidate newCd = initialPopulation.get((int)GAUtils.getRandomDoubleInRange(0, initialPopulation.size() - 1));
            if (!challengerList.contains(newCd)) {
                challengerList.add(newCd);
                i++;
            }
        }
        challengerList.sort(Comparator.comparingDouble(GA1Candidate::getFitness));
        return challengerList.size() != 0 ? challengerList.get(0) : null;
    }

    private GA1Candidate performMutation(GA1Candidate TournamentWinner) {
        double mutationA = GAUtils.getRandomBoolean() ? GAUtils.getRandomMutation() * -1 : GAUtils.getRandomMutation();
        double mutationB = GAUtils.getRandomBoolean() ? GAUtils.getRandomMutation() * -1 : GAUtils.getRandomMutation();
        return new GA1Candidate(TournamentWinner.getMemberA() + (TournamentWinner.getMemberA() * (mutationA / 100)), TournamentWinner.getMemberB() + (TournamentWinner.getMemberB() * (mutationB / 100)));
    }

    private GA1Candidate[] performCrossover(GA1Candidate prevWinner, GA1Candidate curWinner) {
        GA1Candidate crossoverA = new GA1Candidate(prevWinner.getMemberA(), curWinner.getMemberB());
        GA1Candidate crossoverB = new GA1Candidate(prevWinner.getMemberB(), curWinner.getMemberA());
        return new GA1Candidate[]{crossoverA, crossoverB};
    }

    public GA1Candidate getSolution() {
        return mSolution;
    }
}
