/* *************************************************************************
   Copyright 2018 Peter Hoppe

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
************************************************************************* */

package org.ph394b8fe.validator_test.type.int_t;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.ph394b8fe.validator.result.TResult;
import org.ph394b8fe.validator.type.VType;
import org.ph394b8fe.validator.type.impl.int_t.TTypeInt;

/**
 * @author Peter Hoppe
 *
 */
public class Test_TTypeInt
{
    private TTypeInt                fValidator;
    
    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp () throws Exception
    {
        fValidator = (TTypeInt) VType.createInstance (TTypeInt.kKey);
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown () throws Exception
    {
    }

    /**
     * Test method for {@link org.ph394b8fe.validator.type.VType#match(java.lang.String, org.ph394b8fe.validator.result.TResult, int, int, java.lang.String, java.lang.String)}.
     */
    @Test
    public void testMatch ()
    {
        TResult         res;
        
        res = new TResult ();
        fValidator.match ("2", res, 1, 1, "nokey", "int test failed");
        
        
    }

    
}
