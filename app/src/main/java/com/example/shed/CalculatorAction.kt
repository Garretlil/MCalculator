package com.example.shed
 open class CalculatorAction(){
    data class ActionSymbol(val symbol: String):CalculatorAction()
    data class ActionOperation(val operation:EnumOperation):CalculatorAction()
     data class ActionCalc(val calcbutton:EnumCalculation):CalculatorAction()
    //var Clear:CalculatorAction =CalculatorAction()
}
open class EnumCalculation(){ // создаем объекты с одной переменной symbol, каждый объект означает операцию
    object Clear:EnumCalculation()
    object Delete:EnumCalculation()
    object Decimal:EnumCalculation()
    object Calculate:EnumCalculation()
}

open class EnumOperation(val symbol:String){
    open var priority:Int=0
   fun getOper(): EnumOperation
   {
        return  when (symbol) {
           "+" ->  Plus
           "-" ->  Minus
           "*" ->  Multiply
           "/" ->  Divide
           else -> return Plus
       }
   }
    open fun Calc(num1:Double, num2:Double):Double {
        return 0.0
    }
    object Plus:EnumOperation("+"){
         override var priority=0
        override fun Calc(num1:Double,num2:Double):Double{
            return num1+num2
        }
    }
    object Minus:EnumOperation("-"){
        override var priority=0
        override fun Calc(num1:Double,num2:Double):Double{
            return num1-num2
        }
    }
    object Multiply:EnumOperation("*"){
        override var priority=1
          override fun Calc(num1:Double,num2:Double):Double{
               return num1*num2
           }
    }
    object Divide:EnumOperation("/"){
        override var priority=1
        override fun Calc(num1:Double,num2:Double):Double{
            return num1/num2
        }
    }
}
