public class GA1Candidate {
    private double mFitness;
    private double mMemberA;
    private double mMemberB;

    public GA1Candidate() {
        mMemberA = GAUtils.getRandomDoubleInRange(-500, 500);
        mMemberB = GAUtils.getRandomDoubleInRange(-500, 500);
        mFitness = Assess.getTest1(mMemberA, mMemberB);
    }

    public GA1Candidate(double memberA, double memberB) {
        mMemberA = memberA;
        mMemberB = memberB;
        mFitness = Assess.getTest1(mMemberA, mMemberB);
    }

    public void printCandidate() {
        System.out.println("MemberA = " + mMemberA + " MemberB = " + mMemberB + " Fitness = " + mFitness);
    }

    public double getMemberA() {
        return mMemberA;
    }

    public double getMemberB() {
        return mMemberB;
    }

    public double getFitness() {
        return mFitness;
    }
}
