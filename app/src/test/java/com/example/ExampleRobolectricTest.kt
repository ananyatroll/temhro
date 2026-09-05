package com.example

import android.app.Application
import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.StudySubject
import com.example.ui.StudyViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("ተምህሮ / Temhiro", appName)
  }

  @Test
  fun testFreeTrialAccessRulesForAllPackages() {
    val app = ApplicationProvider.getApplicationContext<Application>()
    val viewModel = StudyViewModel(app)

    // Freshman: English 1 & Emerging Tech open, others locked
    val freshmanOpen1 = StudySubject("freshman_english_1", "Communicative English I", "english", "freshman")
    val freshmanOpen2 = StudySubject("freshman_emerging_tech", "Emerging Tech", "tech", "freshman")
    val freshmanLocked = StudySubject("freshman_physics", "General Physics", "physics", "freshman")
    assertFalse(viewModel.isSubjectLocked(freshmanOpen1))
    assertFalse(viewModel.isSubjectLocked(freshmanOpen2))
    assertTrue(viewModel.isSubjectLocked(freshmanLocked))

    // EUEE Natural: Maths & English open, others locked
    val eueeNatOpen1 = StudySubject("euee_nat_maths", "Mathematics", "math", "euee_natural")
    val eueeNatOpen2 = StudySubject("euee_nat_english", "English", "english", "euee_natural")
    val eueeNatLocked = StudySubject("euee_nat_physics", "Physics", "physics", "euee_natural")
    assertFalse(viewModel.isSubjectLocked(eueeNatOpen1))
    assertFalse(viewModel.isSubjectLocked(eueeNatOpen2))
    assertTrue(viewModel.isSubjectLocked(eueeNatLocked))

    // EUEE Social: History & Geography open, others locked
    val eueeSocOpen1 = StudySubject("euee_soc_history", "History", "history", "euee_social")
    val eueeSocOpen2 = StudySubject("euee_soc_geography", "Geography", "geography", "euee_social")
    val eueeSocLocked = StudySubject("euee_soc_economics", "Economics", "economics", "euee_social")
    assertFalse(viewModel.isSubjectLocked(eueeSocOpen1))
    assertFalse(viewModel.isSubjectLocked(eueeSocOpen2))
    assertTrue(viewModel.isSubjectLocked(eueeSocLocked))

    // AAU UAT: Verbal & Quantitative open, others locked
    val uatOpen1 = StudySubject("uat_verbal", "Verbal Reasoning", "verbal", "aau_uat")
    val uatOpen2 = StudySubject("uat_quantitative", "Quantitative Reasoning", "quantitative", "aau_uat")
    val uatLocked = StudySubject("uat_analytical", "Analytical Reasoning", "analytical", "aau_uat")
    assertFalse(viewModel.isSubjectLocked(uatOpen1))
    assertFalse(viewModel.isSubjectLocked(uatOpen2))
    assertTrue(viewModel.isSubjectLocked(uatLocked))

    // Department: Data Structures & Software Eng open, others locked
    val deptOpen1 = StudySubject("dept_data_structures", "Data Structures", "data", "department")
    val deptOpen2 = StudySubject("dept_software_engineering", "Software Engineering", "software", "department")
    val deptLocked = StudySubject("dept_database_systems", "Database Systems", "database", "department")
    assertFalse(viewModel.isSubjectLocked(deptOpen1))
    assertFalse(viewModel.isSubjectLocked(deptOpen2))
    assertTrue(viewModel.isSubjectLocked(deptLocked))

    // Exit Exam: CS & Mgmt open, others locked
    val exitOpen1 = StudySubject("exit_cs", "Computer Science", "cs", "exit_exam")
    val exitOpen2 = StudySubject("exit_mgmt", "Management", "mgmt", "exit_exam")
    val exitLocked = StudySubject("exit_accounting", "Accounting & Finance", "accounting", "exit_exam")
    assertFalse(viewModel.isSubjectLocked(exitOpen1))
    assertFalse(viewModel.isSubjectLocked(exitOpen2))
    assertTrue(viewModel.isSubjectLocked(exitLocked))
  }
}
