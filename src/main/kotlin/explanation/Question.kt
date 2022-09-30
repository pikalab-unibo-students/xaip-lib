package explanation

import Domain
import Operator
import Plan
import Problem
import State

interface Question {
    val problem: Problem
    val plan: Plan
    val focus: Operator
    fun buildHdomain(): Domain
    fun buildHproblem(): Problem
}

class Question1(override val problem: Problem, override val plan: Plan, override val focus: Operator) : Question {
    override fun buildHdomain(): Domain {
        TODO("Not yet implemented")
    }

    override fun buildHproblem(): Problem {
        TODO("Not yet implemented")
    }
}

class Question2(override val problem: Problem, override val plan: Plan, override val focus: Operator) : Question {
    override fun buildHdomain(): Domain {
        TODO("Not yet implemented")
    }

    override fun buildHproblem(): Problem {
        TODO("Not yet implemented")
    }
}

class Question3(override val problem: Problem, override val plan: Plan, override val focus: Operator, val inState: State) : Question {

    override fun buildHdomain(): Domain {
        TODO("Not yet implemented")
    }

    override fun buildHproblem(): Problem {
        TODO("Not yet implemented")
    }
}

class Question4(override val problem: Problem, override val plan: Plan, override val focus: Operator, val alternativePlan: Plan) : Question {
    override fun buildHdomain(): Domain {
        TODO("Not yet implemented")
    }

    override fun buildHproblem(): Problem {
        TODO("Not yet implemented")
    }
}

class Question5(override val problem: Problem, override val plan: Plan, override val focus: Operator, val alternativePlan: Plan) : Question {
    override fun buildHdomain(): Domain {
        TODO("Not yet implemented")
    }

    override fun buildHproblem(): Problem {
        TODO("Not yet implemented")
    }
}
