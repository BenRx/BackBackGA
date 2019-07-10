import java.util.*;

public class GA2 {

    private final static int INITIAL_POPULATION_SIZE = 200;
    private final static int OPERATIONS = 15000;
    private final static int TOURNAMENT_ROUNDS = INITIAL_POPULATION_SIZE / 32;

    private List<GA2Candidate> mInitialPopulation = new ArrayList<>();
    private List<GA2Candidate> mNextPopulation = new ArrayList<>();

    private GA2Candidate mSolution;

    public void start() {
        for (int i = 0 ; i != INITIAL_POPULATION_SIZE ; i++) {
            GA2Candidate newCandidate = new GA2Candidate();
            mInitialPopulation.add(newCandidate);
        }

        for (int i = 0 ; i != OPERATIONS ; i++) {
            GA2Candidate prevSelected = null;
            while (true) {
                GA2Candidate selectedCandidate = performTournamentSelection(mInitialPopulation);
                //GA2Candidate selectedCandidate = performFitnessProportionalSelection(mInitialPopulation);
                if (selectedCandidate == null) {
                    System.out.println("ERROR : NO TOURNAMENT WINNER !");
                    System.exit(1);
                }
                mInitialPopulation.remove(selectedCandidate);

                GA2Candidate mutatedCandidate = performMutation(selectedCandidate);
                mNextPopulation.add(mutatedCandidate);

                if (prevSelected != null) {
                    GA2Candidate[] crossoverResult = performCrossover(prevSelected, selectedCandidate);
                    mNextPopulation.addAll(Arrays.asList(crossoverResult));
                }
                prevSelected = selectedCandidate;
                if (mNextPopulation.size() >= INITIAL_POPULATION_SIZE) {
                    break;
                }
            }

            if (mNextPopulation.size() > INITIAL_POPULATION_SIZE) {
                while (mNextPopulation.size() != INITIAL_POPULATION_SIZE) {
                    mNextPopulation.remove(mNextPopulation.size() - 1);
                }
            }

            mInitialPopulation.clear();
            mInitialPopulation.addAll(mNextPopulation);
            mNextPopulation.clear();
        }
        mInitialPopulation.sort(Comparator.comparingDouble(GA2Candidate::getUtility).reversed());
        //System.out.println("=== RESULTS ===");
        for (GA2Candidate cd : mInitialPopulation) {
            if (mSolution == null && cd.getWeight() <= 500) {
                mSolution = cd;
                break;
            }
        }

        mSolution = mInitialPopulation.get(0);
        /*System.out.println("\n=== SOLUTION ===");
        mSolution.printCandidate();*/
    }

    private GA2Candidate performTournamentSelection(List<GA2Candidate> initialPopulation) {
        List<GA2Candidate> challengerList = new ArrayList<>();

        int i = 0;
        while (i != TOURNAMENT_ROUNDS) {
            GA2Candidate newCd = initialPopulation.get((int)GAUtils.getRandomDoubleInRange(0, initialPopulation.size() - 1));
            if (!challengerList.contains(newCd)) {
                challengerList.add(newCd);
                i++;
            }
        }
        challengerList.sort(Comparator.comparingDouble(GA2Candidate::getUtility).reversed());
        return challengerList.size() != 0 ? challengerList.get(0) : null;
    }

    private GA2Candidate performFitnessProportionalSelection(List<GA2Candidate> population) {
        ArrayList<GA2Pair> probabilityList = new ArrayList<>();
        ArrayList<Double> probabilitySumList = new ArrayList<>();
        double sumUtility = 0;
        int i = 0;
        int j = 0;

        for (GA2Candidate cd : population) {
            sumUtility += cd.getUtility();
        }

        for (GA2Candidate candidate : population) {
            probabilityList.add(i, new GA2Pair(candidate.getUtility() / sumUtility, i));
            i++;
        }

        probabilityList.sort(Comparator.comparingDouble(GA2Pair::getProba));
        Collections.reverse(probabilityList);
        probabilityList.add(0, new GA2Pair(0.0, -1));

        i = 0;
        while (i != probabilityList.size()) {
            j = 0;
            double sum = 0;
            while (j <= i) {
                sum += probabilityList.get(j).getProba();
                j++;
            }
            if ((i + 1) == probabilityList.size()) {
                sum = 1;
            }
            probabilitySumList.add(sum);
            i++;
        }

        double r = GAUtils.getRandomMutation();
        i = 0;
        for (Double value : probabilitySumList) {
            if (r <= value) {
                break;
            }
            i++;
        }

        return population.get(probabilityList.get(i).getIdx());
    }

    private GA2Candidate performMutation(GA2Candidate selectionWinner) {
        double mutationIdx = GAUtils.getRandomDoubleInRange(0, GA2Candidate.BOOL_ARRAY_SIZE - 1);
        boolean[] memberToMutate = selectionWinner.getMember();
        memberToMutate[(int)mutationIdx] = !memberToMutate[(int)mutationIdx];
        return new GA2Candidate(memberToMutate);
    }

    private GA2Candidate[] performCrossover(GA2Candidate prevWinner, GA2Candidate curWinner) {
        boolean[] crossoverA = new boolean[100];
        boolean[] crossoverB = new boolean[100];
        for (int i = 0 ; i != GA2Candidate.BOOL_ARRAY_SIZE / 2 ; i++) {
            crossoverA[i] = prevWinner.getMember()[i];
            crossoverB[i] = curWinner.getMember()[i];
        }
        for (int i = 50 ; i != GA2Candidate.BOOL_ARRAY_SIZE ; i++) {
            crossoverA[i] = curWinner.getMember()[i];
            crossoverB[i] = prevWinner.getMember()[i];
        }
        return new GA2Candidate[]{new GA2Candidate(crossoverA), new GA2Candidate(crossoverB)};
    }

    public GA2Candidate getSolution() {
        return mSolution;
    }
}
