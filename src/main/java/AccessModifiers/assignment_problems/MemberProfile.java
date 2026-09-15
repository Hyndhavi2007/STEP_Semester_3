package AccessModifiers.assignment_problems;
public class MemberProfile {
    private String membershipId;
    private String name;
    private boolean premiumMember;
    private String securityAnswer;

    public MemberProfile() {
        this(null, null);
    }

    public MemberProfile(String name) {
        this(null, name);
    }

    public MemberProfile(String membershipId, String name) {
        this.membershipId = membershipId;
        this.name = name;
        this.premiumMember = false;
        this.securityAnswer = null;
    }

    public String getMembershipId() {
        return membershipId;
    }

    public void setMembershipId(String id) {
        if (membershipId == null) {
            membershipId = id;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPremiumMember() {
        return premiumMember;
    }

    public void setPremiumMember(boolean premium) {
        this.premiumMember = premium;
    }

    public void setSecurityAnswer(String answer) {
        if (answer == null) {
            securityAnswer = null;
        } else {
            securityAnswer = Integer.toString(answer.hashCode());
        }
    }

    public static void main(String[] args) {
        MemberProfile member1 = new MemberProfile("Priya Nair");

        System.out.println(member1.getMembershipId());

        MemberProfile member2 =
                new MemberProfile("LIB-8841", "Priya Nair");

        System.out.println(member2.getMembershipId());

        MemberProfile member3 = new MemberProfile();

        member3.setMembershipId("LIB-8841");
        member3.setMembershipId("FAKE-0000");

        System.out.println(member3.getMembershipId());

        member3.setName("Arun");
        member3.setPremiumMember(true);
        member3.setSecurityAnswer("Blue");

        System.out.println(member3.getName());
        System.out.println(member3.isPremiumMember());
    }
}

