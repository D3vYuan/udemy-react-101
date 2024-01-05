package com.maoluvcode.library.service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.maoluvcode.library.dao.BookRepository;
import com.maoluvcode.library.dao.CheckoutRepository;
import com.maoluvcode.library.dao.HistoryRepository;
import com.maoluvcode.library.dao.PaymentRepository;
import com.maoluvcode.library.dto.ShelfCurrentLoansResponse;
import com.maoluvcode.library.entity.Book;
import com.maoluvcode.library.entity.Checkout;
import com.maoluvcode.library.entity.History;
import com.maoluvcode.library.entity.Payment;

@Service
@Transactional
public class BookService {
    private BookRepository bookRepository;
    private CheckoutRepository checkoutRepository;
    private HistoryRepository historyRepository;
    private PaymentRepository paymentRepository;

    public BookService(BookRepository bookRepository, CheckoutRepository checkoutRepository,
            HistoryRepository historyRepository, PaymentRepository paymentRepository) {
        this.bookRepository = bookRepository;
        this.checkoutRepository = checkoutRepository;
        this.historyRepository = historyRepository;
        this.paymentRepository = paymentRepository;
    }

    public Book checkoutBook(String userEmail, Long bookId) throws Exception {
        Optional<Book> book = bookRepository.findById(bookId);

        Checkout validateCheckout = checkoutRepository.findByUserEmailAndBookId(userEmail, bookId);
        if (!book.isPresent() || validateCheckout != null || book.get().getCopiesAvailable() <= 0) {
            throw new Exception("Book doesn't exists or already checked out by user");
        }

        List<Checkout> currentBooksCheckedOut = checkoutRepository.findBooksByUserEmail(userEmail);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        boolean bookNeedsReturned = false;
        Date todayDate = dateFormat.parse(LocalDate.now().toString());

        for (Checkout checkout : currentBooksCheckedOut) {
            Date returnDate = dateFormat.parse(checkout.getReturnDate());
            TimeUnit time = TimeUnit.DAYS;
            double differenceInTime = time.convert(returnDate.getTime() - todayDate.getTime(), TimeUnit.MILLISECONDS);
            if (differenceInTime < 0) {
                bookNeedsReturned = true;
                break;
            }
        }

        Payment userPayment = paymentRepository.findByUserEmail(userEmail);
        if ((userPayment != null && userPayment.getAmount() > 0) || (userPayment != null && bookNeedsReturned)) {
            throw new Exception("Outstanding fees");
        }

        if (userPayment == null) {
            Payment payment = new Payment();
            payment.setAmount(00.00);
            payment.setUserEmail(userEmail);
            paymentRepository.save(payment);
        }

        book.get().setCopiesAvailable(book.get().getCopiesAvailable() - 1);
        bookRepository.save(book.get());

        Checkout checkout = new Checkout(userEmail, LocalDate.now().toString(), LocalDate.now().plusDays(7).toString(),
                bookId);
        checkoutRepository.save(checkout);
        return book.get();
    }

    public Boolean checkoutBookByUser(String userEmail, Long bookId) {
        Checkout validateCheckout = checkoutRepository.findByUserEmailAndBookId(userEmail, bookId);
        if (validateCheckout != null) {
            return true;
        } else {
            return false;
        }
    }

    public int currentLoansCount(String userEmail) {
        return checkoutRepository.findBooksByUserEmail(userEmail).size();
    }

    public List<ShelfCurrentLoansResponse> currentLoans(String userEmail) throws Exception {
        List<ShelfCurrentLoansResponse> ShelfCurrentLoansResponses = new ArrayList<>();
        List<Checkout> checkoutList = checkoutRepository.findBooksByUserEmail(userEmail);
        List<Long> bookIdList = new ArrayList<>();

        for (Checkout checkout : checkoutList) {
            bookIdList.add(checkout.getBookId());
        }

        List<Book> books = bookRepository.findBooksByBookIds(bookIdList);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        for (Book book : books) {
            Optional<Checkout> checkout = checkoutList.stream().filter(x -> x.getBookId() == book.getId()).findFirst();
            if (checkout.isPresent()) {
                Date d1 = dateFormat.parse(checkout.get().getReturnDate());
                Date d2 = dateFormat.parse(LocalDate.now().toString());

                TimeUnit time = TimeUnit.DAYS;
                long differenceInTime = time.convert(d1.getTime() - d2.getTime(), TimeUnit.MILLISECONDS);

                ShelfCurrentLoansResponses.add(new ShelfCurrentLoansResponse(book, (int) differenceInTime));
            }
        }

        return ShelfCurrentLoansResponses;
    }

    public void returnBook(String userEmail, Long bookId) throws Exception {

        Optional<Book> book = bookRepository.findById(bookId);

        Checkout validateCheckout = checkoutRepository.findByUserEmailAndBookId(userEmail, bookId);

        if (!book.isPresent() || validateCheckout == null) {
            throw new Exception("Book does not exist or not checked out by user");
        }

        book.get().setCopiesAvailable(book.get().getCopiesAvailable() + 1);

        bookRepository.save(book.get());

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date todayDate = dateFormat.parse(LocalDate.now().toString());
        Date returnDate = dateFormat.parse(validateCheckout.getReturnDate());

        TimeUnit time = TimeUnit.DAYS;
        double differenceInTime = time.convert(returnDate.getTime() - todayDate.getTime(), TimeUnit.MILLISECONDS);

        if (differenceInTime < 0) {
            Payment payment = paymentRepository.findByUserEmail(userEmail);
            payment.setAmount(payment.getAmount() + (differenceInTime * -1));
            paymentRepository.save(payment);
        }

        checkoutRepository.deleteById(validateCheckout.getId());

        History history = new History(userEmail, validateCheckout.getCheckoutDate(), LocalDate.now().toString(),
                book.get().getTitle(), book.get().getAuthor(), book.get().getDescription(), book.get().getImg());
        historyRepository.save(history);
    }

    public void renewLoan(String userEmail, Long bookId) throws Exception {
        Checkout validateCheckout = checkoutRepository.findByUserEmailAndBookId(userEmail, bookId);

        if (validateCheckout == null) {
            throw new Exception("Book does not exist or not checked out by user");
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        Date returnDate = dateFormat.parse(validateCheckout.getReturnDate());
        Date todayDate = dateFormat.parse(LocalDate.now().toString());

        if (returnDate.compareTo(todayDate) >= 0) {
            validateCheckout.setReturnDate(LocalDate.now().plusDays(7).toString());
            checkoutRepository.save(validateCheckout);
        }
    }
}
