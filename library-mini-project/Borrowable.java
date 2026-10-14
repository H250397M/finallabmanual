/** Contract for anything that can be lent out (program to an interface). */
public interface Borrowable {
    void borrow(Member member) throws ItemNotAvailableException, MemberLimitExceededException;

    void giveBack();
}
