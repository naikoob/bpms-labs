[condition][driver]There is a Driver=driver : Driver()
[condition][driver]- age less than {age} years old=age < {age}
[condition][driver]- age greater than {age} years old=age > {age}
[condition][driver]- age is at least {age}=age >= {age}
[condition][driver]- age is between {lower} and {upper} years old=age >= {lower}, age <= {upper}
[condition][driver]- has not had any accidents in last 3 years=numberOfAccidents > 0
[condition][driver]- has had more than {number} accidents in last 3 yearw=numberOfAccidents > {number}
[condition][driver]- has more than {number} number of demerit points=numberOfDemeritPoints > {number}
[condition][driver]- has less than or equal to {number} number of demerit points=numberOfDemeritPoints <= {number}
[condition][driver]- has more than {number} years of driving experience=drivingExperience >= {number}

[condition][vehicle]There is a Vehicle=vehicle : Vehicle()
[condition][vehicle]- is equipped with daytime running light=hasDaytimeRunningLights
[condition][vehicle]- annual mileage is more then {number}=annualMileage >= {number}

[condition][policy]There is a Policy=policy : Policy()
[condition][policy]- policy has a driver {driver}=driver == driver
[condition][policy]- policy has a vehicle {vehicle}=vehicle == vehicle
[consequence][]Reject Policy with explanation : '{reason}'=policy.addRejection("{reason}");
[consequence][]Add $ {surcharge} surcharge to Policy, reason: {reason}=policy.addSurcharge("$", {surcharge}, "{reason}");
[consequence][]Add {surcharge}% surcharge to Policy, reason: {reason}=policy.addSurcharge("%", {surcharge}, "{reason}");
[consequence][]Give $ {discount} discount to Policy, reason: {reason}=policy.addDiscount("$", {discount}, "{reason}");
[consequence][]Give {discount}% discount to Policy, reason: {reason}=policy.addDiscount("%", {discount}, "{reason}");

//[consequence][]logRule=System.out.println("the rule that executed is: " + drools.getRule());
