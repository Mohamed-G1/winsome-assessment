package com.nat.winsome_assessment.application.general

sealed class GeneralState {

    /**
     * The equals() -> to compare between to instances To make sure to be only one instance in the memory
     * The hashCode() -> Also to reinforce the comparison to returns the memory address of the object
     * */

    class NoState : GeneralState() {
        override fun equals(other: Any?): Boolean {
            return this === other
        }

        override fun hashCode(): Int {
            return System.identityHashCode(this)
        }
    }


    class InternalServerErrorState : GeneralState() {
        override fun equals(other: Any?): Boolean {
            return this === other
        }

        override fun hashCode(): Int {
            return System.identityHashCode(this)
        }

    }

    class BadRequestState : GeneralState() {
        override fun equals(other: Any?): Boolean {
            return this === other
        }

        override fun hashCode(): Int {
            return System.identityHashCode(this)
        }
    }

    class NotFoundState : GeneralState() {
        override fun equals(other: Any?): Boolean {
            return this === other
        }

        override fun hashCode(): Int {
            return System.identityHashCode(this)
        }
    }

    class SocketTimeoutState : GeneralState() {
        override fun equals(other: Any?): Boolean {
            return this === other
        }

        override fun hashCode(): Int {
            return System.identityHashCode(this)
        }
    }

    class ConnectExceptionState : GeneralState() {
        override fun equals(other: Any?): Boolean {
            return this === other
        }

        override fun hashCode(): Int {
            return System.identityHashCode(this)
        }
    }

    class UnknownHostState : GeneralState() {
        override fun equals(other: Any?): Boolean {
            return this === other
        }

        override fun hashCode(): Int {
            return System.identityHashCode(this)
        }
    }

}

fun defaultGeneralState(): GeneralState = GeneralState.NoState()