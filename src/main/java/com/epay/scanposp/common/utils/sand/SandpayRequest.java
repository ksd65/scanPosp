package com.epay.scanposp.common.utils.sand;

public abstract class SandpayRequest<T extends SandpayResponse>
{
  private SandpayRequestHead head;

  public SandpayRequestHead getHead()
  {
    return this.head; }

  public void setHead(SandpayRequestHead head) {
    this.head = head;
  }

  public abstract Class<T> getResponseClass();

  public abstract String getTxnDesc();
}
