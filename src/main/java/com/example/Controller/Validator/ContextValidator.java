package com.example.Controller.Validator;

public class ContextValidator {
    //private Strategy strategy;
    public ContextValidator(){};

    /**
     * Creates a validator for a specific strategy
     * @param strategy the strategy used to create a validator
     * @return a validator for a specific strategy
     */
    public static AbstractValidator createValidator(Strategy strategy){
      switch(strategy) {
          case PERSONE:
              return new PersoneValidator();
          case USER:
              return new UserValidator();
          case RELATIONSHIP:
              return new RelationshipValidator();
          case MESSAGE:
              return new MessageValidator();
      }
      return null;
    }

}
