/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.fineract.integrationtests;


import org.apache.fineract.accounting.closure.command.GLClosureCommand;
import org.apache.fineract.accounting.closure.data.IncomeAndExpenseJournalEntryData;
import org.apache.fineract.accounting.closure.domain.GLClosureRepository;
import org.apache.fineract.accounting.closure.exception.RunningBalanceNotCalculatedException;
import org.apache.fineract.accounting.closure.serialization.GLClosureCommandFromApiJsonDeserializer;
import org.apache.fineract.accounting.closure.service.CalculateIncomeAndExpenseBookingImpl;
import org.apache.fineract.accounting.closure.service.IncomeAndExpenseReadPlatformService;
import org.apache.fineract.accounting.glaccount.domain.GLAccountRepositoryWrapper;
import org.apache.fineract.infrastructure.core.api.JsonQuery;
import org.apache.fineract.organisation.office.domain.OfficeRepositoryWrapper;
import org.apache.fineract.organisation.office.service.OfficeReadPlatformService;
import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * JUnit Test Cases for Account Transfer for.
 */
public class CalculateIncomeAndExpanseBookingImplTest {

    /*
    Test class CalculateIncomeAndExpenseBookingImpl
     */
    @Autowired
    private CalculateIncomeAndExpenseBookingImpl calculateIncomeAndExpenseBooking;
    private GLClosureCommandFromApiJsonDeserializer fromApiJsonDeserializer;
    private OfficeRepositoryWrapper officeRepository;
    private GLClosureRepository glClosureRepository;
    private GLAccountRepositoryWrapper glAccountRepository;
    private IncomeAndExpenseReadPlatformService incomeAndExpenseReadPlatformService;
    private OfficeReadPlatformService officeReadPlatformService;

    @Before
    public void setup() {
    }

    @After
    public void tearDown() {

    }

    /*
    Case 1: All running balances has to be calculated before booking off income and expense account
    No exception is to be thrown since it is running for the input.
    However, later a null pointer exception is thrown, since the object incomeAndExpense is not initialized.
         */
    @Test(expected = Exception.class)
    public void testBookOffIncomeAndExpense() {
        JsonQuery query = new JsonQuery("select command to extract income and expanse", null, null);
        GLClosureCommand glClosureCommand =  new GLClosureCommand((long)10,(long)10, new LocalDate(),"Closing comment",  false, (long)10 , "CAD", false, false, "comment" );
        IncomeAndExpenseJournalEntryData incomeAndExpenseJournalEntryData= new IncomeAndExpenseJournalEntryData(null, null,null, null
        , true, true, null,null,null,10,10,null,null);
        List<IncomeAndExpenseJournalEntryData> incomeAndExpenseJournalEntryDataList = new ArrayList<>();
        incomeAndExpenseJournalEntryDataList.add(incomeAndExpenseJournalEntryData);
        calculateIncomeAndExpenseBooking.bookOffIncomeAndExpense(incomeAndExpenseJournalEntryDataList, glClosureCommand, false,null,null);

    }

    /*
        Case 2: All running balances has to be calculated before booking off income and expense account
        If not running balances, then throw exception
    */
    @Test(expected = RunningBalanceNotCalculatedException.class)
    public void testBookOffIncomeAndExpenseCheckException() {
        JsonQuery query = new JsonQuery("select command to extract income and expanse", null, null);
        GLClosureCommand glClosureCommand =  new GLClosureCommand((long)10,(long)10, new LocalDate(),"Closing comment",  false, (long)10 , "CAD", false, false, "comment" );
        IncomeAndExpenseJournalEntryData incomeAndExpenseJournalEntryData= new IncomeAndExpenseJournalEntryData(null, null,null, null
                , true, true, null,null,null,10,10,null,null);
        List<IncomeAndExpenseJournalEntryData> incomeAndExpenseJournalEntryDataList = new ArrayList<IncomeAndExpenseJournalEntryData>();
        incomeAndExpenseJournalEntryDataList.add(incomeAndExpenseJournalEntryData);
        calculateIncomeAndExpenseBooking.bookOffIncomeAndExpense(incomeAndExpenseJournalEntryDataList,glClosureCommand, false,null,null);

    }

}