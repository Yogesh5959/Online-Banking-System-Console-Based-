public  boolean equals(Object obj)
    {
        if(obj==null)return false;
        if(obj instanceof Account)
        {
            Account a2=(Account)obj;
            String accountNumber2=(String)a2.accountNumber;
            String accountHolderName2=(String)a2.accountHolderName;
            if(this.accountHolderName.equals(accountHolderName2)&&accountNumber.equals(accountNumber2))
            {
                return true;
            }
            return false;
        }
        return false;

    }