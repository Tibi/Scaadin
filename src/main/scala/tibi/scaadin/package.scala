package tibi
package object scaadin {
	
  def box[T](obj: T): Option[T] = if (obj == null) None else Some(obj)

  def toInt(obj: AnyRef): Option[Int] = try { Some(obj.toString.toInt) } catch {
    case _: NumberFormatException ⇒ None
  }
  
  def toLong(obj: AnyRef): Option[Long] = try { Some(obj.toString.toLong) } catch {
    case _: NumberFormatException ⇒ None
  }
  
}