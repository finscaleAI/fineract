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
import org.apache.fineract.accounting.closure.service.CalculateIncomeAndExpenseBookingImpl;
import org.apache.fineract.accounting.closure.service.IncomeAndExpenseReadPlatformService;
import org.apache.fineract.accounting.glaccount.domain.GLAccountRepositoryWrapper;
import org.apache.fineract.integrationtests.common.Utils;
import org.apache.fineract.organisation.office.domain.OfficeRepositoryWrapper;
import org.apache.fineract.organisation.office.service.OfficeReadPlatformService;
import org.junit.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CalculateIncomeAndExpanseBookingIntegrationTest {
    /*
    Test class CalculateIncomeAndExpenseBookingImpl
     */

    @Mock private OfficeRepositoryWrapper officeRepository;
    @Mock private GLClosureRepository glClosureRepository;
    @Mock private GLAccountRepositoryWrapper glAccountRepository;
    @Mock private IncomeAndExpenseReadPlatformService incomeAndExpenseReadPlatformService;
    @Mock private OfficeReadPlatformService officeReadPlatformService;

    @InjectMocks
    private CalculateIncomeAndExpenseBookingImpl calculateIncomeAndExpenseBooking;

    @Before
    public void setup() {
        calculateIncomeAndExpenseBooking =  new CalculateIncomeAndExpenseBookingImpl(null, officeRepository, glClosureRepository, glAccountRepository, incomeAndExpenseReadPlatformService,officeReadPlatformService);

    }

    @After
    public void tearDown() {
    }
    /*
        Case 1: All running balances has to be calculated before booking off income and expense account
        If not running balances, then throw exception
    */
    @Test(expected = RunningBalanceNotCalculatedException.class)
    public void testBookOffIncomeAndExpenseCheckException() {
        GLClosureCommand glClosureCommand =  new GLClosureCommand((long)10,(long)10, new LocalDate(),"Closing comment",  false, (long)10 , "CAD", false, false, "comment" );
        IncomeAndExpenseJournalEntryData incomeAndExpenseJournalEntryData= new IncomeAndExpenseJournalEntryData(null, null,null, null
                , true, false, null,null,null,10,10,null,null);
        List<IncomeAndExpenseJournalEntryData> incomeAndExpenseJournalEntryDataList = new ArrayList<IncomeAndExpenseJournalEntryData>();
        incomeAndExpenseJournalEntryDataList.add(incomeAndExpenseJournalEntryData);
        calculateIncomeAndExpenseBooking.bookOffIncomeAndExpense(incomeAndExpenseJournalEntryDataList,glClosureCommand, false,null,null);
    }

    /*
    Case 2: All running balances has to be calculated before booking off income and expense account
    No exception is to be thrown since it is running for the input.
    However, later a null pointer exception is thrown, since the object incomeAndExpense is not initialized.
         */
    @Test
    public void testBookOffIncomeAndExpense() {
        GLClosureCommand glClosureCommand =  new GLClosureCommand(10L, 10L, new LocalDate(), "Closing comment", false, 10L , "CAD", false, false, "comment" );
        IncomeAndExpenseJournalEntryData incomeAndExpenseJournalEntryData= new IncomeAndExpenseJournalEntryData(null, null,null, null
                , true, true, null,null,null,10,10,null,null);
        List<IncomeAndExpenseJournalEntryData> incomeAndExpenseJournalEntryDataList = new ArrayList<>();
        incomeAndExpenseJournalEntryDataList.add(incomeAndExpenseJournalEntryData);
        Assert.assertNotNull(calculateIncomeAndExpenseBooking.bookOffIncomeAndExpense(incomeAndExpenseJournalEntryDataList, glClosureCommand, false,null,null));
    }



}
