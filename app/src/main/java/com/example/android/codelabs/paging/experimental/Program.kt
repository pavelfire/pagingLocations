package com.example.android.codelabs.paging.experimental

class EggVoice : Thread() {
    override fun run() {
        for (i in 0..4) {
            try {
                sleep(1000) //Приостанавливает поток на 1 секунду
            } catch (e: InterruptedException) {
            }
            println("яйцо!")
        }
        //Слово «яйцо» сказано 5 раз
    }
}

object ChickenVoice //Класс с методом main()
{
    var mAnotherOpinion //Побочный поток
            : EggVoice? = null

    @JvmStatic
    fun main(args: Array<String>) {
        mAnotherOpinion = EggVoice() //Создание потока
        println("Спор начат...")
        mAnotherOpinion!!.start() //Запуск потока
        for (i in 0..4) {
            try {
                Thread.sleep(1000) //Приостанавливает поток на 1 секунду
            } catch (e: InterruptedException) {
            }
            println("курица!")
        }

        //Слово «курица» сказано 5 раз
        if (mAnotherOpinion!!.isAlive) //Если оппонент еще не сказал последнее слово
        {
            try {
                mAnotherOpinion!!.join() //Подождать пока оппонент закончит высказываться.
            } catch (e: InterruptedException) {
            }
            println("Первым появилось яйцо!")
        } else  //если оппонент уже закончил высказываться
        {
            println("Первой появилась курица!")
        }
        println("Спор закончен!")
    }
}