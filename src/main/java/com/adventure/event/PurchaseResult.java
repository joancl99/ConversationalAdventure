package com.adventure.event;

public class PurchaseResult {

    public enum Status { SUCCESS, INSUFFICIENT_FUNDS }

    private final Status status;

    private PurchaseResult(Status status) { this.status = status; }

    public static PurchaseResult success()            { return new PurchaseResult(Status.SUCCESS); }
    public static PurchaseResult insufficientFunds()  { return new PurchaseResult(Status.INSUFFICIENT_FUNDS); }

    public boolean isSuccess() { return status == Status.SUCCESS; }
    public Status getStatus()  { return status; }
}
