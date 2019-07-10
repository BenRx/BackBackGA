import java.util.*;

public class GA2Candidate {
    public static final int BOOL_ARRAY_SIZE = 100;
    public static final double MAX_WEIGHT = 500.0;
    private final double WEIGHT_PEN = 2;

    private double mWeight;
    private double mUtility;
    private boolean[] mMember;

    public GA2Candidate() {
        mMember = new boolean[BOOL_ARRAY_SIZE];
        for(int i = 0 ; i < mMember.length ; i++){
            mMember[i] = false;
        }

        double[] tmp = Assess.getTest2(mMember);
        mWeight = tmp[0];

        mUtility = mWeight > MAX_WEIGHT ? tmp[1] / (mWeight / WEIGHT_PEN) : tmp[1];
    }

    public GA2Candidate(boolean[] member) {
        mMember = member;

        double[] tmp = Assess.getTest2(mMember);
        mWeight = tmp[0];
        mUtility = mWeight > MAX_WEIGHT ? tmp[1] / (mWeight / WEIGHT_PEN) : tmp[1];
    }

    public void printCandidate() {
        System.out.println("Weight = " + mWeight + " Utility = " + mUtility + " member = " + Arrays.toString(mMember));
    }

    public double getUtility() {
        return mUtility;
    }

    public double getWeight() {
        return mWeight;
    }

    public boolean[] getMember() {
        return mMember;
    }
}
