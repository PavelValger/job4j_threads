package ru.job4j.cash;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Optional;

@ThreadSafe
public class AccountStorage {
    @GuardedBy("this")
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
        return accounts.putIfAbsent(account.id(), account) == null;
    }

    public synchronized boolean update(Account account) {
        return accounts.put(account.id(), account) != null;
    }

    public synchronized boolean delete(int id) {
        return accounts.remove(id) != null;
    }

    public synchronized Optional<Account> getById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public boolean transfer(int fromId, int toId, int amount) {
        Account first = null;
        Optional<Account> optionalFirst = getById(fromId);
        if (optionalFirst.isPresent()) {
            first = optionalFirst.get();
        }
        Account second = null;
        Optional<Account> optionalSecond = getById(toId);
        if (optionalSecond.isPresent()) {
            second = optionalSecond.get();
        }
        boolean rsl = false;
        if (first != null && second != null) {
            rsl = first.amount() - amount >= 0;
            if (rsl) {
                update(new Account(first.id(), first.amount() - amount));
                update(new Account(second.id(), second.amount() + amount));
            }
        }
        return rsl;
    }
}
