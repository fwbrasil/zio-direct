package zio.asyncawait.core.metaprog

import scala.quoted._
import zio.asyncawait.core.metaprog.Extractors.Lambda1
import zio.asyncawait.core.util.Format

object ValDefStatement:
  def unapply(using Quotes)(stmt: quotes.reflect.Statement): Option[(quotes.reflect.Symbol, quotes.reflect.Term)] =
    import quotes.reflect._
    stmt match {
      case valdef @ ValDef(name, tpe, rhsOpt) =>
        val body =
          rhsOpt match {
            // TODO Better site-description in error
            case None      => report.throwError(s"Cannot parse 'val' clause with no '= rhs' (i.e. equals and right hand side) of ${Printer.TreeStructure.show(stmt)}")
            case Some(rhs) => rhs
          }

        Some((valdef.symbol, body))

      case _ => None
    }
end ValDefStatement
