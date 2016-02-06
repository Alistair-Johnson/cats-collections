package dogs

import Predef._

trait Order[A] { self =>
  import Order._
  /**
   * Compare `l` and `r` values returning:
   * Order.LT when `l` is less than `r`
   * Order.EQ when `l` is equal to than `r`
   * Order.GT when `l` is greater than `r`
   */
  def apply(l: A, r: A): Order.Ordering

  /**
   * Compare `l` and `r` values returning:
   * Order.LT when `l` is less than `r`
   * Order.EQ when `l` is equal to than `r`
   * Order.GT when `l` is greater than `r`
   */
  def compare(l: A, r: A): Order.Ordering = apply(l,r)

  /**
   * Given a function that can produce an A from a B, return an
   * Order[B] which uses this ordering after converting Bs to As
   */
  def contramap[B](f: B => A): Order[B] = new Order[B] {
    override def apply(l: B, r: B) = self(f(l), f(r))
  }

  def eq(l: A, r: A): Boolean = apply(l, r) == EQ

  def min(l: A, r: A): A = compare(l,r) match {
    case LT => l
    case _ => r
  }
}

object Order {
  sealed trait Ordering
  case object LT extends Ordering
  case object EQ extends Ordering
  case object GT extends Ordering

  def apply[A](implicit A: Order[A]): Order[A] = A
}
