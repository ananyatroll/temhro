package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.AbsoluteCutCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ads.InterstitialAdManager
import com.example.ads.AdsManager
import com.example.data.StudySubject
import com.example.ui.NotificationHelper
import com.example.ui.StudyViewModel
import com.example.ui.TranslationManager
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import com.example.ui.components.*
import com.example.ui.theme.*

// Styling a smooth rounded shape
val HexagonChamferShape = RoundedCornerShape(16.dp)

@Composable
fun DashboardScreen(viewModel: StudyViewModel) {
    val progress by viewModel.userProgress.collectAsState()
    val subjectsList by viewModel.subjects.collectAsState()
    val subjectProgressMap by viewModel.subjectProgressMap.collectAsState()
    val completedSubjectIds = remember(progress.completedSubjects) {
        progress.completedSubjects.split(",").filter { it.isNotEmpty() }.toSet()
    }

    val studentName by viewModel.studentName.collectAsState()
    val studentGoal by viewModel.studentGoal.collectAsState()
    val currentLang by viewModel.currentLanguage.collectAsState()
    val isDarkTheme by viewModel.isDarkTheme.collectAsState()
    val selectedSemesterFilter by viewModel.selectedSemesterFilter.collectAsState()

    fun t(key: String): String = TranslationManager.get(key, currentLang)

    val academicDepartment by viewModel.academicDepartment.collectAsState()
    val academicYear by viewModel.academicYear.collectAsState()

    // Department Curriculum Definition by Year and Semester
    val departmentCurriculum = remember(academicDepartment) {
        val isEng = academicDepartment.contains("Engineering", ignoreCase = true)
        val dept = academicDepartment.ifBlank { "Department" }

        fun s(id: String, name: String, icon: String) = StudySubject(id, name, icon, "department")

        when {
            // BAIS (Business Administration and Information Systems)
            dept.contains("BAIS", ignoreCase = true) || dept.contains("Business Administration and Information", ignoreCase = true) -> listOf(
                "Year 2" to listOf(
                    ("Semester 1" to "Sem 1") to listOf(
                        s("bais_y2_s1_mgmt", "Introduction to Management", "management"),
                        s("bais_y2_s1_is", "Introduction to Information System", "analytics"),
                        s("bais_y2_s1_stats", "Business Statistics I", "analytics"),
                        s("bais_y2_s1_net1", "Computer Networking I", "computer"),
                        s("bais_y2_s1_acc1", "Fundamental of Accounting I", "accounting"),
                        s("bais_y2_s1_micro", "Microeconomics", "economics")
                    ),
                    ("Semester 2" to "Sem 2") to listOf(
                        s("bais_y2_s2_hrm", "Human Resources Management", "management"),
                        s("bais_y2_s2_acc2", "Fundamental of Accounting II", "accounting"),
                        s("bais_y2_s2_comm", "Business Communication", "english"),
                        s("bais_y2_s2_net2", "Computer Networking II", "computer"),
                        s("bais_y2_s2_mkt", "Principle of Marketing", "marketing"),
                        s("bais_y2_s2_stats2", "Business Statistics II", "analytics"),
                        s("bais_y2_s2_ob", "Organizational Behavior (OB)", "management")
                    )
                ),
                "Year 3" to listOf(
                    ("Semester 1" to "Sem 1") to listOf(
                        s("bais_y3_s1_or", "Operations Research", "analytics"),
                        s("bais_y3_s1_mat_mgmt", "Materials Management", "management"),
                        s("bais_y3_s1_prog1", "Programming I", "computer"),
                        s("bais_y3_s1_risk", "Risk Management and Insurance", "business"),
                        s("bais_y3_s1_sad", "Fundamentals of System Analysis and Design", "software"),
                        s("bais_y3_s1_db1", "Database System I", "analytics")
                    ),
                    ("Semester 2" to "Sem 2") to listOf(
                        s("bais_y3_s2_cyber_law", "Business and Cyber Law", "civics"),
                        s("bais_y3_s2_brm", "Business Research Methods", "business"),
                        s("bais_y3_s2_db2", "Database System II", "analytics"),
                        s("bais_y3_s2_ecom", "E-Commerce", "computer"),
                        s("bais_y3_s2_oosad", "Object Oriented Systems Analysis & Design", "software"),
                        s("bais_y3_s2_web", "Web Page Development", "computer")
                    )
                ),
                "Year 4" to listOf(
                    ("Semester 1" to "Sem 1") to listOf(
                        s("bais_y4_s1_prog2", "Programming II", "computer"),
                        s("bais_y4_s1_om", "Operation Management", "management"),
                        s("bais_y4_s1_win_prog", "Windows Programming", "computer"),
                        s("bais_y4_s1_bi_dss", "Business Intelligence & Decision Support System", "analytics"),
                        s("bais_y4_s1_sec", "Information System Security & Privacy", "computer"),
                        s("bais_y4_s1_cma", "Cost and Managerial Accounting", "accounting")
                    ),
                    ("Semester 2" to "Sem 2") to listOf(
                        s("bais_y4_s2_pm", "Project Management", "management"),
                        s("bais_y4_s2_fin", "Financial Management", "business"),
                        s("bais_y4_s2_sm", "Strategic Management", "management"),
                        s("bais_y4_s2_is_retrieval", "Introduction to Info Storage & Retrieval", "analytics"),
                        s("bais_y4_s2_ism", "Information System Management", "computer"),
                        s("bais_y4_s2_adv_entrep", "Advanced Entrepreneurship & Innovation", "business"),
                        s("bais_y4_s2_hist", "History of Ethiopia", "civics"),
                        s("bais_y4_s2_proj", "Senior Project", "software")
                    )
                )
            )

            // Management
            dept.contains("Management", ignoreCase = true) && !dept.contains("Marketing", ignoreCase = true) && !dept.contains("Logistics", ignoreCase = true) && !dept.contains("BAIS", ignoreCase = true) && !dept.contains("Supply", ignoreCase = true) -> listOf(
                "Year 2" to listOf(
                    ("Semester 1" to "Sem 1") to listOf(
                        s("mgmt_y2_s1_app", "Computer Applications in Management", "computer"),
                        s("mgmt_y2_s1_writing", "Basic Writing Skills", "english"),
                        s("mgmt_y2_s1_micro", "Microeconomics", "economics"),
                        s("mgmt_y2_s1_intro", "Introduction to Management", "management"),
                        s("mgmt_y2_s1_org_theory", "Organization Theory", "management"),
                        s("mgmt_y2_s1_admin_comm", "Administrative & Business Communication", "english"),
                        s("mgmt_y2_s1_stats1", "Statistics for Management I", "analytics")
                    ),
                    ("Semester 2" to "Sem 2") to listOf(
                        s("mgmt_y2_s2_math", "Mathematics for Management", "maths"),
                        s("mgmt_y2_s2_mkt", "Principle of Marketing", "marketing"),
                        s("mgmt_y2_s2_acc1", "Fundamentals of Accounting I", "accounting"),
                        s("mgmt_y2_s2_stats2", "Statistics for Management II", "analytics"),
                        s("mgmt_y2_s2_macro", "Macroeconomics", "economics"),
                        s("mgmt_y2_s2_ob", "Organizational Behavior", "management")
                    )
                ),
                "Year 3" to listOf(
                    ("Semester 1" to "Sem 1") to listOf(
                        s("mgmt_y3_s1_mat_mgmt", "Materials Management", "management"),
                        s("mgmt_y3_s1_hrm", "Human Resource Management", "management"),
                        s("mgmt_y3_s1_intl_mkt", "International Marketing", "marketing"),
                        s("mgmt_y3_s1_acc2", "Fundamentals of Accounting II", "accounting"),
                        s("mgmt_y3_s1_mis", "Management Information System", "analytics"),
                        s("mgmt_y3_s1_econ_mgmt", "Econometrics for Management", "analytics")
                    ),
                    ("Semester 2" to "Sem 2") to listOf(
                        s("mgmt_y3_s2_lead_change", "Leadership & Change Management", "management"),
                        s("mgmt_y3_s2_cma1", "Cost and Management Accounting I", "accounting"),
                        s("mgmt_y3_s2_brm", "Business Research Methods", "business"),
                        s("mgmt_y3_s2_sad", "System Analysis and Design", "software"),
                        s("mgmt_y3_s2_law", "Business Law", "civics"),
                        s("mgmt_y3_s2_mgr_econ", "Managerial Economics", "economics")
                    )
                ),
                "Year 4" to listOf(
                    ("Semester 1" to "Sem 1") to listOf(
                        s("mgmt_y4_s1_intern", "Internship in Management", "business"),
                        s("mgmt_y4_s1_csr", "Business Ethics & CSR", "civics"),
                        s("mgmt_y4_s1_cma2", "Cost and Management Accounting II", "accounting"),
                        s("mgmt_y4_s1_or", "Operations Research", "analytics"),
                        s("mgmt_y4_s1_fin_mgmt", "Financial Management", "business"),
                        s("mgmt_y4_s1_risk", "Risk Management and Insurance", "business"),
                        s("mgmt_y4_s1_res1", "Research in Management I", "business")
                    ),
                    ("Semester 2" to "Sem 2") to listOf(
                        s("mgmt_y4_s2_om", "Operations Management", "management"),
                        s("mgmt_y4_s2_fin_inst", "Management of Financial Institutions", "business"),
                        s("mgmt_y4_s2_innov_entrep", "Innovation Management & Entrepreneurship", "business"),
                        s("mgmt_y4_s2_pm", "Project Management", "management"),
                        s("mgmt_y4_s2_sm", "Strategic Management", "management"),
                        s("mgmt_y4_s2_res2", "Research in Management II", "business")
                    )
                )
            )

            // Logistics and Supply Chain Management (LSCM)
            dept.contains("Logistics", ignoreCase = true) || dept.contains("Supply Chain", ignoreCase = true) || dept.contains("LSCM", ignoreCase = true) -> listOf(
                "Year 2" to listOf(
                    ("Semester 1" to "Sem 1") to listOf(
                        s("lscm_y2_s1_acc1", "Fundamental Accounting Principle One", "accounting"),
                        s("lscm_y2_s1_scm", "Essential Supply Chain Management", "logistics"),
                        s("lscm_y2_s1_stats1", "Basic Statistics One", "analytics"),
                        s("lscm_y2_s1_log_mgmt", "Fundamentals Of Logistics Management", "logistics"),
                        s("lscm_y2_s1_comp_app", "Computer Application In Management", "computer"),
                        s("lscm_y2_s1_mkt", "Principles Of Marketing", "marketing"),
                        s("lscm_y2_s1_intro_mgmt", "Introduction To Management", "management")
                    ),
                    ("Semester 2" to "Sem 2") to listOf(
                        s("lscm_y2_s2_ob", "Organizational Behavior", "management"),
                        s("lscm_y2_s2_proc_mgmt", "Basics Of Procurement Management", "logistics"),
                        s("lscm_y2_s2_mgr_stats", "Managerial Statistics", "analytics"),
                        s("lscm_y2_s2_comm", "Business Communication", "english"),
                        s("lscm_y2_s2_neg_contract", "Negotiations And Contract Management", "civics"),
                        s("lscm_y2_s2_acc2", "Fundamental Accounting 2", "accounting"),
                        s("lscm_y2_s2_micro", "Micro Economics", "economics")
                    )
                ),
                "Year 3" to listOf(
                    ("Semester 1" to "Sem 1") to listOf(
                        s("lscm_y3_s1_gov_proc", "Government Procurement", "logistics"),
                        s("lscm_y3_s1_wh_mgmt", "Warehouse Management", "logistics"),
                        s("lscm_y3_s1_law", "Business Law", "civics"),
                        s("lscm_y3_s1_inv_mgmt", "Inventory Management", "logistics"),
                        s("lscm_y3_s1_trans_mgmt", "Transportation Management", "logistics"),
                        s("lscm_y3_s1_cma1", "Cost and Management Accounting 1", "accounting"),
                        s("lscm_y3_s1_math", "Business Mathematics", "maths")
                    ),
                    ("Semester 2" to "Sem 2") to listOf(
                        s("lscm_y3_s2_econometrics", "Introduction to Econometrics", "analytics"),
                        s("lscm_y3_s2_risk", "Supply Chain Risk & Insurance Management", "business"),
                        s("lscm_y3_s2_fin1", "Financial Management I", "business"),
                        s("lscm_y3_s2_foreign_proc", "Foreign Procurement", "logistics"),
                        s("lscm_y3_s2_res_methods", "Research Methods in SCM", "business"),
                        s("lscm_y3_s2_strat_sourcing", "Strategic Sourcing & Supplier Relationship", "logistics"),
                        s("lscm_y3_s2_ecom_scis", "E-Commerce & Supply Chain Info System", "computer")
                    )
                ),
                "Year 4" to listOf(
                    ("Semester 1" to "Sem 1") to listOf(
                        s("lscm_y4_s1_or", "Operation Research", "analytics"),
                        s("lscm_y4_s1_custom_freight", "Custom Clearance & Freight Forwarding", "logistics"),
                        s("lscm_y4_s1_port_terminal", "Port And Terminal Operation Management", "logistics"),
                        s("lscm_y4_s1_pm", "Project Management", "management"),
                        s("lscm_y4_s1_hist", "History of Ethiopia", "civics"),
                        s("lscm_y4_s1_sust_scm", "Sustainable Supply Chain Management", "logistics"),
                        s("lscm_y4_s1_attachment", "Practical Attachment & Seminar of LSCM", "business")
                    ),
                    ("Semester 2" to "Sem 2") to listOf(
                        s("lscm_y4_s2_om", "Operation Management", "management"),
                        s("lscm_y4_s2_strat_scm", "Strategic Supply Chain Management", "logistics"),
                        s("lscm_y4_s2_humanitarian", "Humanitarian Logistics Management", "logistics"),
                        s("lscm_y4_s2_global_scm", "Global Supply Chain Management", "logistics"),
                        s("lscm_y4_s2_research", "Final Research Project", "business"),
                        s("lscm_y4_s2_dist_log", "Distribution Logistics Management", "logistics")
                    )
                )
            )

            // Economics
            dept.contains("Economics", ignoreCase = true) -> listOf(
                "Year 2" to listOf(
                    ("Semester 1" to "Sem 1") to listOf(
                        s("econ_y2_s1_acc1", "Principle Of Accounting I", "accounting"),
                        s("econ_y2_s1_comp", "Basic Computer Skills", "computer"),
                        s("econ_y2_s1_calc", "Calculus For Economists", "maths"),
                        s("econ_y2_s1_stats", "Introduction To Statistics", "analytics"),
                        s("econ_y2_s1_macro1", "Macroeconomics I", "economics"),
                        s("econ_y2_s1_micro1", "Microeconomics I", "economics")
                    ),
                    ("Semester 2" to "Sem 2") to listOf(
                        s("econ_y2_s2_acc2", "Accounting II", "accounting"),
                        s("econ_y2_s2_writing", "Basic Writing Skills", "english"),
                        s("econ_y2_s2_linalg", "Linear Algebra", "maths"),
                        s("econ_y2_s2_macro2", "Macroeconomics II", "economics"),
                        s("econ_y2_s2_micro2", "Microeconomics II", "economics"),
                        s("econ_y2_s2_stats_econ", "Statistics for Economics", "analytics")
                    )
                ),
                "Year 3" to listOf(
                    ("Semester 1" to "Sem 1") to listOf(
                        s("econ_y3_s1_dev1", "Developmental Economics I", "economics"),
                        s("econ_y3_s1_metrics1", "Econometrics I", "analytics"),
                        s("econ_y3_s1_fin_econ", "Financial Economics", "business"),
                        s("econ_y3_s1_intl1", "International Economics I", "economics"),
                        s("econ_y3_s1_intro_mgmt", "Introduction To Management", "management"),
                        s("econ_y3_s1_labor", "Labor Economics", "economics"),
                        s("econ_y3_s1_math_econ", "Mathematical Economics", "maths")
                    ),
                    ("Semester 2" to "Sem 2") to listOf(
                        s("econ_y3_s2_dev2", "Development Economics II", "economics"),
                        s("econ_y3_s2_metrics2", "Econometrics II", "analytics"),
                        s("econ_y3_s2_ind_econ", "Industrial Economics", "economics"),
                        s("econ_y3_s2_env_econ", "Environmental Economics", "economics"),
                        s("econ_y3_s2_intl2", "International Economics II", "economics"),
                        s("econ_y3_s2_res_methods", "Research Methods", "business")
                    )
                ),
                "Year 4" to listOf(
                    ("Semester 1" to "Sem 1") to listOf(
                        s("econ_y4_s1_agri", "Agricultural Economics", "economics"),
                        s("econ_y4_s1_behav", "Behavioral Economics", "economics"),
                        s("econ_y4_s1_monetary", "Monetary Economics", "business"),
                        s("econ_y4_s1_planning", "Economic Planning", "economics"),
                        s("econ_y4_s1_stata", "STATA & Applied Statistics", "analytics"),
                        s("econ_y4_s1_thought1", "History of Economic Thought", "economics"),
                        s("econ_y4_s1_thesis1", "Undergraduate Thesis I", "economics")
                    ),
                    ("Semester 2" to "Sem 2") to listOf(
                        s("econ_y4_s2_proj", "Senior Project", "business"),
                        s("econ_y4_s2_thought2", "History of Economic Thought II", "economics"),
                        s("econ_y4_s2_thesis2", "Final Thesis Defense", "economics"),
                        s("econ_y4_s2_pub_fin", "Public Finance", "business"),
                        s("econ_y4_s2_urban", "Urban Economics", "economics"),
                        s("econ_y4_s2_rural", "Rural Economics", "economics")
                    )
                )
            )

            // Accounting & Finance
            dept.contains("Accounting", ignoreCase = true) -> listOf(
                "Year 2" to listOf(
                    ("Semester 1" to "Sem 1") to listOf(
                        s("acc_y2_s1_macro", "Macro Economics", "economics"),
                        s("acc_y2_s1_math", "Business Maths", "maths"),
                        s("acc_y2_s1_is", "Fundamental Of Information System", "analytics"),
                        s("acc_y2_s1_acc1", "Fundamental Of Accounting 1", "accounting"),
                        s("acc_y2_s1_stats", "Basic Statistics", "analytics"),
                        s("acc_y2_s1_mgmt", "Introduction Of Management", "management")
                    ),
                    ("Semester 2" to "Sem 2") to listOf(
                        s("acc_y2_s2_acc2", "Fundamental Of Accounting 2", "accounting"),
                        s("acc_y2_s2_or", "Operation Research", "analytics"),
                        s("acc_y2_s2_mkt", "Principle Of Marketing", "marketing"),
                        s("acc_y2_s2_stats", "Business Statistics", "analytics"),
                        s("acc_y2_s2_risk", "Risk Management And Insurance", "business"),
                        s("acc_y2_s2_law", "Business Law", "civics")
                    )
                ),
                "Year 3" to listOf(
                    ("Semester 1" to "Sem 1") to listOf(
                        s("acc_y3_s1_fin_mgmt1", "Financial Management 1", "business"),
                        s("acc_y3_s1_res_methods", "Research Methods For Accounting", "business"),
                        s("acc_y3_s1_fin_inst", "Financial Institution And Markets", "business"),
                        s("acc_y3_s1_inter_acc1", "Intermediate Financial Accounting 1", "accounting"),
                        s("acc_y3_s1_cma1", "Cost And Management Accounting 1", "accounting"),
                        s("acc_y3_s1_pub_sector", "Accounting For Public Sector & NGO", "accounting")
                    ),
                    ("Semester 2" to "Sem 2") to listOf(
                        s("acc_y3_s2_inter_acc2", "Intermediate Financial Accounting 2", "accounting"),
                        s("acc_y3_s2_om", "Operation Management", "management"),
                        s("acc_y3_s2_hist", "History of Ethiopia", "civics"),
                        s("acc_y3_s2_modeling", "Financial Modeling", "analytics"),
                        s("acc_y3_s2_econometrics", "Econometrics For Finance", "analytics"),
                        s("acc_y3_s2_cost_acc2", "Cost Accounting 2", "accounting"),
                        s("acc_y3_s2_fin_mgmt2", "Financial Management 2", "business")
                    )
                ),
                "Year 4" to listOf(
                    ("Semester 1" to "Sem 1") to listOf(
                        s("acc_y4_s1_res1", "Senior Research Project 1", "business"),
                        s("acc_y4_s1_invest", "Investment Analysis & Portfolio Mgmt", "business"),
                        s("acc_y4_s1_tax", "Public Finance & Taxation", "accounting"),
                        s("acc_y4_s1_adv_acc1", "Advanced Financial Accounting 1", "accounting"),
                        s("acc_y4_s1_ais", "Accounting Information System", "analytics"),
                        s("acc_y4_s1_intern", "Internship Report", "business"),
                        s("acc_y4_s1_audit1", "Auditing Principles And Practices I", "accounting")
                    ),
                    ("Semester 2" to "Sem 2") to listOf(
                        s("acc_y4_s2_software", "Accounting Software Application", "computer"),
                        s("acc_y4_s2_audit2", "Auditing Principles And Practice II", "accounting"),
                        s("acc_y4_s2_proj_eval", "Project Analysis And Evaluation", "business"),
                        s("acc_y4_s2_sm", "Strategic Management", "management"),
                        s("acc_y4_s2_adv_acc2", "Advanced Financial Accounting 2", "accounting"),
                        s("acc_y4_s2_res2", "Senior Research Project 2", "business"),
                        s("acc_y4_s2_comm", "Contemporary Business Communication", "english")
                    )
                )
            )

            // Marketing Management
            dept.contains("Marketing", ignoreCase = true) -> listOf(
                "Year 2" to listOf(
                    ("Semester 1" to "Sem 1") to listOf(
                        s("mkt_y2_s1_mkt", "Principle Of Marketing", "marketing"),
                        s("mkt_y2_s1_cb", "Consumer Behavior", "marketing"),
                        s("mkt_y2_s1_ict", "Introduction To ICT", "computer"),
                        s("mkt_y2_s1_mgmt", "Introduction To Management", "management"),
                        s("mkt_y2_s1_micro", "Microeconomics", "economics"),
                        s("mkt_y2_s1_law", "Business Law", "civics")
                    ),
                    ("Semester 2" to "Sem 2") to listOf(
                        s("mkt_y2_s2_retail", "Retail Management", "marketing"),
                        s("mkt_y2_s2_sales", "Sales Management", "marketing"),
                        s("mkt_y2_s2_imc", "Integrated Marketing Communication", "marketing"),
                        s("mkt_y2_s2_comm", "Business Communication", "english"),
                        s("mkt_y2_s2_risk", "Risk Management And Insurance", "business"),
                        s("mkt_y2_s2_ob", "Organizational Behavior", "management")
                    )
                ),
                "Year 3" to listOf(
                    ("Semester 1" to "Sem 1") to listOf(
                        s("mkt_y3_s1_math", "Business Mathematics", "maths"),
                        s("mkt_y3_s1_acc1", "Fundamentals Of Accounting 1", "accounting"),
                        s("mkt_y3_s1_social_mkt", "Social Marketing", "marketing"),
                        s("mkt_y3_s1_emarketing", "E-Marketing", "marketing"),
                        s("mkt_y3_s1_services", "Services Marketing", "marketing"),
                        s("mkt_y3_s1_mgr_mkt", "Managerial Marketing", "marketing")
                    ),
                    ("Semester 2" to "Sem 2") to listOf(
                        s("mkt_y3_s2_pm", "Project Management", "management"),
                        s("mkt_y3_s2_mis", "Marketing Information System", "analytics"),
                        s("mkt_y3_s2_acc2", "Fundamentals Of Accounting 2", "accounting"),
                        s("mkt_y3_s2_research", "Marketing Research", "marketing"),
                        s("mkt_y3_s2_event", "Event Management", "marketing"),
                        s("mkt_y3_s2_tourism", "Tourism & Hospitality Marketing", "marketing")
                    )
                ),
                "Year 4" to listOf(
                    ("Semester 1" to "Sem 1") to listOf(
                        s("mkt_y4_s1_apprentice", "Apprenticeship In Marketing", "marketing"),
                        s("mkt_y4_s1_b2b", "Business Marketing", "marketing"),
                        s("mkt_y4_s1_fin", "Financial Management", "business"),
                        s("mkt_y4_s1_channels", "Marketing Channel & Logistics Mgmt", "logistics"),
                        s("mkt_y4_s1_intl_mkt", "International Marketing", "marketing"),
                        s("mkt_y4_s1_hist", "History of Ethiopia and the Horn", "civics"),
                        s("mkt_y4_s1_brand", "Product & Brand Management", "marketing")
                    ),
                    ("Semester 2" to "Sem 2") to listOf(
                        s("mkt_y4_s2_import_export", "Import/Export Policy & Procedure", "business"),
                        s("mkt_y4_s2_agri_mkt", "Agricultural & Commodity Marketing", "marketing"),
                        s("mkt_y4_s2_strat_mkt", "Strategic Marketing Management", "marketing"),
                        s("mkt_y4_s2_essay2", "Senior Essay II", "business"),
                        s("mkt_y4_s2_negotiation", "Negotiation Management", "management"),
                        s("mkt_y4_s2_om", "Operations Management", "management")
                    )
                )
            )

            // Computer Science & Software Engineering Fallback
            dept.contains("Computer Science", ignoreCase = true) || dept.contains("Software", ignoreCase = true) -> listOf(
                "Year 2" to listOf(
                    ("Semester 1" to "Sem 1") to listOf(s("dept_dsa", "Data Structures & Algorithms", "computer"), s("dept_oop", "Object Oriented Programming", "software")),
                    ("Semester 2" to "Sem 2") to listOf(s("dept_db_sys", "Database Systems & SQL", "analytics"), s("dept_comp_org", "Computer Architecture", "computer"))
                ),
                "Year 3" to listOf(
                    ("Semester 1" to "Sem 1") to listOf(s("dept_os", "Operating Systems", "computer"), s("dept_networks", "Computer Networks", "computer")),
                    ("Semester 2" to "Sem 2") to listOf(s("dept_soft_eng", "Software Engineering", "software"), s("dept_web_dev", "Web Architecture", "computer"))
                ),
                "Year 4" to listOf(
                    ("Semester 1" to "Sem 1") to listOf(s("dept_distributed_sys", "Distributed Systems", "computer"), s("dept_ai_ml", "Artificial Intelligence", "computer")),
                    ("Semester 2" to "Sem 2") to listOf(s("dept_cybersecurity", "Information Security", "computer"), s("dept_capstone_1", "Senior Capstone Project", "software"))
                )
            )

            // General Fallback
            else -> listOf(
                "Year 2" to listOf(
                    ("Semester 1" to "Sem 1") to listOf(s("dept_core_1", "$dept Core Fundamentals I", "computer"), s("dept_methods_1", "Research Methods I", "analytics")),
                    ("Semester 2" to "Sem 2") to listOf(s("dept_theory_1", "$dept Core Fundamentals II", "management"), s("dept_ethics_1", "Professional Ethics", "civics"))
                ),
                "Year 3" to listOf(
                    ("Semester 1" to "Sem 1") to listOf(s("dept_core_2", "Advanced $dept Studies I", "computer"), s("dept_methods_2", "Statistical Modeling", "analytics")),
                    ("Semester 2" to "Sem 2") to listOf(s("dept_project_prep", "Departmental Studio", "management"), s("dept_applied_policy", "Policy Framework", "civics"))
                ),
                "Year 4" to listOf(
                    ("Semester 1" to "Sem 1") to listOf(s("dept_seminar_adv", "Senior Academic Seminar", "management"), s("dept_internship_app", "Professional Practicum", "business")),
                    ("Semester 2" to "Sem 2") to listOf(s("dept_strategic_mgmt", "Strategic Systems Management", "management"), s("dept_grad_thesis", "Graduation Research Project", "computer"))
                )
            )
        }
    }

    val enrolledYearNumber = remember(academicYear) {
        when {
            academicYear.contains("5") -> 5
            academicYear.contains("4") -> 4
            academicYear.contains("3") -> 3
            else -> 2
        }
    }

    val isDepartmentMode = progress.activePackageId == "department" || progress.activePackageId == "exit_exam"

    val bgModifier = Modifier.background(Color.Transparent)

    // Outer layout with custom background gradients or dark reader canvas
    Box(
        modifier = Modifier
            .fillMaxSize()
            .then(bgModifier)
    ) {
        val showCelebration by viewModel.showCompletionParticles.collectAsState()
        if (showCelebration) {
            CompletionParticleEffect()
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(top = 24.dp, bottom = 100.dp)
        ) {
            // Header item containing Greeting and dropdowns (span across both columns)
            item(span = { GridItemSpan(maxLineSpan) }) {
                Column {
                    GreetingHeader(viewModel = viewModel, username = studentName.ifEmpty { progress.username })
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            // Header item containing Section Title
            item(span = { GridItemSpan(maxLineSpan) }) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val sectionTitle = when (progress.activePackageId) {
                        "euee_natural", "euee_social" -> t("high_yield_topics")
                        "freshman_natural", "freshman_social" -> t("freshman_curriculum")
                        "aau_uat" -> t("aau_uat_matrix")
                        "department" -> if (academicDepartment.isNotBlank()) "${academicDepartment.uppercase()} CURRICULUM" else t("dept_course_matrix")
                        "exit_exam" -> t("exit_exam_blueprints")
                        else -> t("course_syllabus")
                    }

                    Text(
                        text = sectionTitle,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Black,
                            fontSize = 14.sp,
                            letterSpacing = (-0.2).sp
                        ),
                        maxLines = 1,
                        overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
                        color = if (progress.activePackageId == "exit_exam") Color(0xFFDC2626) else if (isDarkTheme) Color.White else IndigoSecondary,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = java.lang.String.format(t("view_all_progress"), completedSubjectIds.size.toString(), subjectsList.size.toString()),
                        style = MaterialTheme.typography.labelMedium,
                        color = EmeraldDark,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1
                    )
                }
            }

            // Spacing below header
            item(span = { GridItemSpan(maxLineSpan) }) {
                Spacer(modifier = Modifier.height(12.dp))
            }

            if (isDepartmentMode) {
                // Multi-Year Department Curriculum Section
                departmentCurriculum.forEach { (yearLabel, semestersList) ->
                    val yearNum = when {
                        yearLabel.contains("5") -> 5
                        yearLabel.contains("4") -> 4
                        yearLabel.contains("3") -> 3
                        else -> 2
                    }
                    val isYearLocked = yearNum > enrolledYearNumber && progress.paymentStatus != "approved"

                    // Year Header Banner
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp, bottom = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = if (isYearLocked) Color(0xFF475569) else EmeraldPrimary,
                                modifier = Modifier.padding(end = 12.dp)
                            ) {
                                Text(
                                    text = yearLabel.uppercase(),
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Black),
                                    color = Color.White
                                )
                            }
                            HorizontalDivider(
                                modifier = Modifier.weight(1f),
                                color = if (isDarkTheme) Color(0xFF334155) else Color(0xFFE2E8F0),
                                thickness = 1.dp
                            )
                            if (isYearLocked) {
                                Spacer(modifier = Modifier.width(8.dp))
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Lock,
                                        contentDescription = "Locked",
                                        tint = Color(0xFFEF4444),
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Text(
                                        text = "Upgrade to Unlock",
                                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                        color = Color(0xFFEF4444),
                                        fontSize = 10.sp
                                    )
                                }
                            }
                        }
                    }

                    // Semesters for this year
                    semestersList.forEach { (semInfo, semCourses) ->
                        val (semTitle, semBadge) = semInfo

                        // Semester Sub-Header Divider
                        item(span = { GridItemSpan(maxLineSpan) }) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 10.dp, bottom = 4.dp, start = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = if (isDarkTheme) Color(0xFF334155) else Color(0xFFE2E8F0)
                                ) {
                                    Text(
                                        text = semTitle.uppercase(),
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                        color = if (isDarkTheme) Color(0xFF94A3B8) else Color(0xFF475569),
                                        fontSize = 11.sp
                                    )
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                HorizontalDivider(
                                    modifier = Modifier.weight(1f),
                                    color = if (isDarkTheme) Color(0xFF1E293B) else Color(0xFFF1F5F9),
                                    thickness = 1.dp
                                )
                            }
                        }

                        // Courses for this semester
                        items(semCourses) { subject ->
                            val isDone = completedSubjectIds.contains(subject.id)
                            val context = LocalContext.current

                            SubjectHexCard(
                                subjectName = subject.name,
                                iconName = subject.icon,
                                isCompleted = isDone,
                                isLocked = isYearLocked,
                                semesterBadge = semBadge,
                                progress = if (isDone) 1f else 0f,
                                isDarkTheme = isDarkTheme,
                                currentLang = currentLang,
                                onSubjectClick = {
                                    if (isYearLocked) {
                                        viewModel.paywallPackageIdForUpgrade.value = "department"
                                        viewModel.showFreeTrialPaywall.value = true
                                    } else {
                                        val activity = AdsManager.findActivity(context)
                                        if (activity != null) {
                                            InterstitialAdManager.showIfAllowed(activity) {
                                                viewModel.selectSubject(subject)
                                            }
                                        } else {
                                            viewModel.selectSubject(subject)
                                        }
                                    }
                                }
                            )
                        }
                    }
                }

                // Exit Exam Preparation Gateway at the bottom of the department senior curriculum
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isDarkTheme) Color(0xFF1E293B) else Color(0xFFEFF6FF)
                        ),
                        border = BorderStroke(1.5.dp, Color(0xFF3B82F6)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 24.dp)
                            .clickable {
                                viewModel.enrollInPackage("exit_exam")
                            }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(18.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "PREPARING FOR GRADUATION?",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.Black,
                                        letterSpacing = 1.sp
                                    ),
                                    color = Color(0xFF3B82F6)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "National Exit Exam Preparation",
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                    color = if (isDarkTheme) Color.White else Color(0xFF1E293B)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Blueprints, ministry practice exams & model questions tailored for $academicDepartment.",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = if (isDarkTheme) TextMuted else Color(0xFF475569)
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Button(
                                onClick = {
                                    viewModel.enrollInPackage("exit_exam")
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3B82F6)),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text("Enroll", color = Color.White, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            } else if (subjectsList.isEmpty()) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isDarkTheme) Color(0xFF1E293B) else Color.White
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.MenuBook,
                                contentDescription = null,
                                tint = EmeraldPrimary,
                                modifier = Modifier.size(44.dp)
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = t("empty_preparing_modules"),
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = if (isDarkTheme) Color.White else IndigoSecondary
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = t("empty_select_preferred"),
                                style = MaterialTheme.typography.bodySmall,
                                color = if (isDarkTheme) TextMuted else Color.Gray,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(
                                onClick = {
                                    viewModel.enrollInPackage("euee_natural")
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text(t("empty_load_subjects"), color = Color.White, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            } else {
                val currentPkg = progress.activePackageId ?: ""
                val isSemesterBoundPackage = currentPkg.startsWith("freshman") || currentPkg == "department"

                if (isSemesterBoundPackage) {
                    val natSem1Order = listOf(
                        "freshman_nat_english_1",
                        "freshman_nat_psychology",
                        "freshman_nat_geography",
                        "freshman_nat_critical_thinking",
                        "freshman_nat_physical_fitness",
                        "freshman_nat_maths",
                        "freshman_nat_physics",
                        "freshman_nat_history"
                    )
                    val natSem2Order = listOf(
                        "freshman_nat_applied_maths",
                        "freshman_nat_english_2",
                        "freshman_nat_computer_programming",
                        "freshman_nat_entrepreneurship",
                        "freshman_nat_emerging_tech",
                        "freshman_nat_anthropology",
                        "freshman_nat_civics",
                        "freshman_nat_biology",
                        "freshman_nat_chemistry"
                    )

                    val socSem1Order = listOf(
                        "freshman_soc_civics",
                        "freshman_soc_anthropology",
                        "freshman_soc_english_1",
                        "freshman_soc_global_trends",
                        "freshman_soc_economics",
                        "freshman_soc_emerging_tech",
                        "freshman_soc_entrepreneurship"
                    )
                    val socSem2Order = listOf(
                        "freshman_soc_geography",
                        "freshman_soc_history",
                        "freshman_soc_inclusiveness",
                        "freshman_soc_physical_fitness",
                        "freshman_soc_english_2",
                        "freshman_soc_psychology",
                        "freshman_soc_maths",
                        "freshman_soc_critical_thinking"
                    )

                    val sem1Subjects = subjectsList.filter { subject ->
                        when (subject.packageId) {
                            "freshman_natural" -> natSem1Order.contains(subject.id)
                            "freshman_social" -> socSem1Order.contains(subject.id)
                            else -> subject.id.contains("_1") || subject.id.contains("_dsa") || subject.id.contains("_oop") || subject.id.contains("_circuit_1") || subject.id.contains("_thermodynamics_1") || subject.id.contains("_constitutional_law")
                        }
                    }.sortedWith(
                        // Unlocked / Free trial courses appear first, followed by defined curriculum order
                        compareBy<StudySubject> { viewModel.isSubjectLocked(it) }
                            .thenBy {
                                val idx = if (it.packageId == "freshman_social") socSem1Order.indexOf(it.id) else natSem1Order.indexOf(it.id)
                                if (idx >= 0) idx else 99
                            }
                    )

                    val sem2Subjects = subjectsList.filter { subject ->
                        when (subject.packageId) {
                            "freshman_natural" -> natSem2Order.contains(subject.id)
                            "freshman_social" -> socSem2Order.contains(subject.id)
                            else -> !(subject.id.contains("_1") || subject.id.contains("_dsa") || subject.id.contains("_oop") || subject.id.contains("_circuit_1") || subject.id.contains("_thermodynamics_1") || subject.id.contains("_constitutional_law"))
                        }
                    }.sortedWith(
                        // Unlocked / Free trial courses appear first, followed by defined curriculum order
                        compareBy<StudySubject> { viewModel.isSubjectLocked(it) }
                            .thenBy {
                                val idx = if (it.packageId == "freshman_social") socSem2Order.indexOf(it.id) else natSem2Order.indexOf(it.id)
                                if (idx >= 0) idx else 99
                            }
                    )

                    if (sem1Subjects.isNotEmpty()) {
                        item(span = { GridItemSpan(maxLineSpan) }) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 8.dp, bottom = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = EmeraldPrimary,
                                    modifier = Modifier.padding(end = 12.dp)
                                ) {
                                    Text(
                                        text = "SEMESTER 1 COURSES",
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Black),
                                        color = Color.White
                                    )
                                }
                                HorizontalDivider(
                                    modifier = Modifier.weight(1f),
                                    color = if (isDarkTheme) Color(0xFF334155) else Color(0xFFE2E8F0),
                                    thickness = 1.dp
                                )
                            }
                        }

                        items(sem1Subjects) { subject ->
                            val isDone = completedSubjectIds.contains(subject.id)
                            val isLocked = viewModel.isSubjectLocked(subject)
                            val progressVal = subjectProgressMap[subject.id] ?: if (isDone) 1f else 0f
                            val context = LocalContext.current
                            SubjectHexCard(
                                subjectName = subject.name,
                                iconName = subject.icon,
                                isCompleted = isDone,
                                isLocked = isLocked,
                                semesterBadge = "Sem 1",
                                progress = progressVal,
                                isDarkTheme = isDarkTheme,
                                currentLang = currentLang,
                                onSubjectClick = {
                                    val activity = AdsManager.findActivity(context)
                                    if (activity != null) {
                                        InterstitialAdManager.showIfAllowed(activity) {
                                            viewModel.selectSubject(subject)
                                        }
                                    } else {
                                        viewModel.selectSubject(subject)
                                    }
                                }
                            )
                        }
                    }

                    if (sem2Subjects.isNotEmpty()) {
                        item(span = { GridItemSpan(maxLineSpan) }) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 16.dp, bottom = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = Color(0xFF3B82F6),
                                    modifier = Modifier.padding(end = 12.dp)
                                ) {
                                    Text(
                                        text = "SEMESTER 2 COURSES",
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Black),
                                        color = Color.White
                                    )
                                }
                                HorizontalDivider(
                                    modifier = Modifier.weight(1f),
                                    color = if (isDarkTheme) Color(0xFF334155) else Color(0xFFE2E8F0),
                                    thickness = 1.dp
                                )
                            }
                        }

                        items(sem2Subjects) { subject ->
                            val isDone = completedSubjectIds.contains(subject.id)
                            val isLocked = viewModel.isSubjectLocked(subject)
                            val progressVal = subjectProgressMap[subject.id] ?: if (isDone) 1f else 0f
                            val context = LocalContext.current
                            SubjectHexCard(
                                subjectName = subject.name,
                                iconName = subject.icon,
                                isCompleted = isDone,
                                isLocked = isLocked,
                                semesterBadge = "Sem 2",
                                progress = progressVal,
                                isDarkTheme = isDarkTheme,
                                currentLang = currentLang,
                                onSubjectClick = {
                                    val activity = AdsManager.findActivity(context)
                                    if (activity != null) {
                                        InterstitialAdManager.showIfAllowed(activity) {
                                            viewModel.selectSubject(subject)
                                        }
                                    } else {
                                        viewModel.selectSubject(subject)
                                    }
                                }
                            )
                        }
                    }

                    if (otherSubjects.isNotEmpty()) {
                        items(otherSubjects) { subject ->
                            val isDone = completedSubjectIds.contains(subject.id)
                            val isLocked = viewModel.isSubjectLocked(subject)
                            val progressVal = subjectProgressMap[subject.id] ?: if (isDone) 1f else 0f
                            val context = LocalContext.current
                            SubjectHexCard(
                                subjectName = subject.name,
                                iconName = subject.icon,
                                isCompleted = isDone,
                                isLocked = isLocked,
                                semesterBadge = null,
                                progress = progressVal,
                                isDarkTheme = isDarkTheme,
                                currentLang = currentLang,
                                onSubjectClick = {
                                    val activity = AdsManager.findActivity(context)
                                    if (activity != null) {
                                        InterstitialAdManager.showIfAllowed(activity) {
                                            viewModel.selectSubject(subject)
                                        }
                                    } else {
                                        viewModel.selectSubject(subject)
                                    }
                                }
                            )
                        }
                    }
                } else {
                    // Non-semester packages (EUEE, COC, Exit Exam): Clean sorted list without semester headers or badges
                    val sortedSubjects = subjectsList.sortedBy { viewModel.isSubjectLocked(it) }
                    items(sortedSubjects) { subject ->
                        val isDone = completedSubjectIds.contains(subject.id)
                        val isLocked = viewModel.isSubjectLocked(subject)
                        val progressVal = subjectProgressMap[subject.id] ?: if (isDone) 1f else 0f
                        val context = LocalContext.current
                        SubjectHexCard(
                            subjectName = subject.name,
                            iconName = subject.icon,
                            isCompleted = isDone,
                            isLocked = isLocked,
                            semesterBadge = null,
                            progress = progressVal,
                            isDarkTheme = isDarkTheme,
                            currentLang = currentLang,
                            onSubjectClick = {
                                val activity = AdsManager.findActivity(context)
                                if (activity != null) {
                                    InterstitialAdManager.showIfAllowed(activity) {
                                        viewModel.selectSubject(subject)
                                    }
                                } else {
                                    viewModel.selectSubject(subject)
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun GreetingHeader(viewModel: StudyViewModel, username: String) {
    val currentLang by viewModel.currentLanguage.collectAsState()
    val isDarkTheme by viewModel.isDarkTheme.collectAsState()
    var langMenuExpanded by remember { mutableStateOf(false) }

    fun t(key: String): String = TranslationManager.get(key, currentLang)

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.weight(1f)
        ) {
            // Tools Sidebar Menu Button (Opens full study suite sidebar drawer)
            Surface(
                modifier = Modifier
                    .size(42.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .border(1.5.dp, EmeraldPrimary.copy(alpha = 0.6f), RoundedCornerShape(12.dp))
                    .clickable { viewModel.openToolsSidebar() }
                    .testTag("tools_sidebar_toggle"),
                color = if (isDarkTheme) CardBgDark else Color(0xFFF0FDF4),
                shape = RoundedCornerShape(12.dp),
                shadowElevation = 3.dp
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Open Study Tools Sidebar",
                        tint = EmeraldPrimary,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Column(
                modifier = Modifier.weight(1f, fill = false),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = t("home").uppercase(),
                    style = MaterialTheme.typography.labelSmall.copy(
                        letterSpacing = 2.sp,
                        color = if (isDarkTheme) TextMuted else IndigoSecondary.copy(alpha = 0.6f),
                        fontWeight = FontWeight.Bold
                    ),
                    maxLines = 1,
                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                )
                Text(
                    text = "${t("greeting_prefix")}$username",
                    style = MaterialTheme.typography.displayLarge.copy(
                        fontWeight = FontWeight.Black,
                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                        lineHeight = 26.sp,
                        fontSize = 20.sp,
                        letterSpacing = (-0.4).sp
                    ),
                    color = if (isDarkTheme) Color.White else IndigoSecondary,
                    maxLines = 1,
                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                )
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val context = LocalContext.current
            var showLoginCelebrationModal by remember { mutableStateOf(false) }

            // Trigger login celebration animation & notification once per calendar day
            LaunchedEffect(Unit) {
                kotlinx.coroutines.delay(600)
                val awarded = viewModel.checkAndClaimDailyLoginBonus()
                if (awarded) {
                    showLoginCelebrationModal = true
                    NotificationHelper.sendMotivationalNotification(
                        context = context,
                        studentName = username.ifEmpty { "Student" },
                        studentGoal = "Master high-yield topics & conquer your exams"
                    )
                }
            }

            // Gamified Streak & XP Pill (Duolingo / Phantom Wallet Style)
            val answeredCount = viewModel.answeredQuestionsSet.collectAsState().value.size
            val readNotesCount = viewModel.readNotesSet.collectAsState().value.size
            val totalXp = (answeredCount * 15) + (readNotesCount * 25)
            val streakDays by viewModel.dailyStreakCount.collectAsState()

            val infiniteTransition = rememberInfiniteTransition(label = "streakPulse")
            val flameScale by infiniteTransition.animateFloat(
                initialValue = 1.0f,
                targetValue = 1.18f,
                animationSpec = infiniteRepeatable(animation = tween(800, easing = LinearOutSlowInEasing), repeatMode = RepeatMode.Reverse),
                label = "flamePulse"
            )

            // Gamified Streak Pill (Interactive tap opens XP celebration)
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(if (isDarkTheme) Color(0xFF1E293B) else Color(0xFFFFFBEB))
                    .border(1.dp, GoldAccent.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
                    .pressBounce(pressedScale = 0.94f)
                    .clickable { showLoginCelebrationModal = true }
                    .padding(horizontal = 8.dp, vertical = 6.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.LocalFireDepartment,
                        contentDescription = "Streak",
                        tint = Color(0xFFF97316),
                        modifier = Modifier
                            .size(18.dp)
                            .graphicsLayer {
                                scaleX = flameScale
                                scaleY = flameScale
                            }
                    )
                    Text(
                        text = "$streakDays",
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Black),
                        color = if (isDarkTheme) GoldLight else Color(0xFFC2410C)
                    )
                }
            }

            // Doughnut Free Trial Timer Badge next to streak flame
            val trialActivatedAt by viewModel.freeTrialActivatedAtMillis.collectAsState()
            if (trialActivatedAt > 0L) {
                var remainingMs by remember { mutableStateOf(viewModel.getFreeTrialRemainingMillis()) }
                var showTimerModal by remember { mutableStateOf(false) }

                LaunchedEffect(trialActivatedAt) {
                    while (true) {
                        remainingMs = viewModel.getFreeTrialRemainingMillis()
                        kotlinx.coroutines.delay(1000L)
                    }
                }

                val totalDurationMs = 72L * 3600L * 1000L
                val progressFraction = (remainingMs.toFloat() / totalDurationMs.toFloat()).coerceIn(0f, 1f)
                val hoursLeft = (remainingMs / (1000 * 3600)).toInt()

                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(if (isDarkTheme) Color(0xFF1E293B) else Color(0xFFF0FDF4))
                        .border(1.dp, EmeraldPrimary.copy(alpha = 0.4f), CircleShape)
                        .clickable { showTimerModal = true },
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        progress = { progressFraction },
                        modifier = Modifier.fillMaxSize(),
                        color = EmeraldPrimary,
                        trackColor = if (isDarkTheme) Color(0xFF334155) else Color(0xFFE2E8F0),
                        strokeWidth = 3.dp,
                        strokeCap = StrokeCap.Round
                    )
                    Text(
                        text = "${hoursLeft}h",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Black,
                            fontSize = 10.sp
                        ),
                        color = if (isDarkTheme) HolographicAqua else EmeraldDark
                    )
                }

                if (showTimerModal) {
                    val totalSec = (remainingMs / 1000).toInt()
                    val h = totalSec / 3600
                    val m = (totalSec % 3600) / 60
                    val s = totalSec % 60
                    val timerStr = String.format("%02dh : %02dm : %02ds", h, m, s)

                    AlertDialog(
                        onDismissRequest = { showTimerModal = false },
                        containerColor = if (isDarkTheme) CardBgDark else Color.White,
                        icon = {
                            Icon(
                                imageVector = Icons.Default.Timer,
                                contentDescription = null,
                                tint = EmeraldPrimary,
                                modifier = Modifier.size(36.dp)
                            )
                        },
                        title = {
                            Text(
                                text = "Free Trial Countdown",
                                fontWeight = FontWeight.Black,
                                fontSize = 18.sp,
                                textAlign = TextAlign.Center,
                                color = if (isDarkTheme) Color.White else IndigoSecondary
                            )
                        },
                        text = {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "72-Hour Full Pass Active",
                                    fontSize = 13.sp,
                                    color = TextMuted,
                                    textAlign = TextAlign.Center
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                                Surface(
                                    shape = RoundedCornerShape(12.dp),
                                    color = EmeraldPrimary.copy(alpha = 0.1f),
                                    border = BorderStroke(1.dp, EmeraldPrimary.copy(alpha = 0.3f)),
                                    modifier = Modifier.padding(horizontal = 8.dp)
                                ) {
                                    Text(
                                        text = timerStr,
                                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
                                        style = MaterialTheme.typography.titleLarge.copy(
                                            fontWeight = FontWeight.Black,
                                            fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                                            fontSize = 20.sp
                                        ),
                                        color = EmeraldPrimary
                                    )
                                }
                                Spacer(modifier = Modifier.height(12.dp))
                                Text(
                                    text = "Upgrade anytime to unlock lifetime unlimited access.",
                                    fontSize = 11.sp,
                                    color = TextMuted,
                                    textAlign = TextAlign.Center
                                )
                            }
                        },
                        confirmButton = {
                            Button(
                                onClick = {
                                    showTimerModal = false
                                    viewModel.showFreeTrialPaywall.value = true
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Upgrade to Premium", fontWeight = FontWeight.Bold)
                            }
                        },
                        dismissButton = {
                            TextButton(
                                onClick = { showTimerModal = false },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Close", color = TextMuted)
                            }
                        }
                    )
                }
            }

            // Animated Login Reward Dialog
            if (showLoginCelebrationModal) {
                AlertDialog(
                    onDismissRequest = { showLoginCelebrationModal = false },
                    containerColor = if (isDarkTheme) CardBgDark else Color.White,
                    title = {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocalFireDepartment,
                                contentDescription = "Fire",
                                tint = Color(0xFFF97316),
                                modifier = Modifier.size(54.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Welcome Back, ${username.ifEmpty { "Scholar" }}! 🎉",
                                fontWeight = FontWeight.Black,
                                fontSize = 20.sp,
                                textAlign = TextAlign.Center,
                                color = if (isDarkTheme) Color.White else IndigoSecondary
                            )
                        }
                    },
                    text = {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Daily Streak Active: $streakDays Days!",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = EmeraldPrimary,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Surface(
                                shape = RoundedCornerShape(14.dp),
                                color = EmeraldPrimary.copy(alpha = 0.12f),
                                border = BorderStroke(1.dp, EmeraldPrimary.copy(alpha = 0.3f)),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier.padding(14.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(Icons.Default.Bolt, null, tint = GoldAccent, modifier = Modifier.size(24.dp))
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text("Daily Login Bonus", fontWeight = FontWeight.Bold, color = if (isDarkTheme) Color.White else Color.Black)
                                    }
                                    Text("+50 XP", fontWeight = FontWeight.Black, color = EmeraldPrimary, fontSize = 16.sp)
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Total Progress Score: $totalXp XP",
                                fontSize = 12.sp,
                                color = TextMuted
                            )
                        }
                    },
                    confirmButton = {
                        Button(
                            onClick = { showLoginCelebrationModal = false },
                            colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Keep Learning! 🚀", fontWeight = FontWeight.Bold)
                        }
                    }
                )
            }


            // Dark / Light Mode symbol toggle (pure symbols, no text)
            Box(
                modifier = Modifier
                    .clip(HexagonChamferShape)
                    .background(if (isDarkTheme) CardBgDark else Color.White)
                    .pressBounce(pressedScale = 0.92f)
                    .clickable { viewModel.toggleDarkTheme() }
                    .padding(horizontal = 10.dp, vertical = 7.dp)
                    .border(1.dp, if (isDarkTheme) EmeraldPrimary.copy(alpha = 0.35f) else IndigoLight, HexagonChamferShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (isDarkTheme) Icons.Default.DarkMode else Icons.Default.WbSunny,
                    contentDescription = "Toggle Theme",
                    tint = if (isDarkTheme) HolographicAqua else GoldDark,
                    modifier = Modifier.size(20.dp)
                )
            }

            // Localized Language selector symbol button
            Box {
                Box(
                    modifier = Modifier
                        .clip(HexagonChamferShape)
                        .background(if (isDarkTheme) CardBgDark else Color.White)
                        .pressBounce(pressedScale = 0.94f)
                        .clickable { langMenuExpanded = true }
                        .padding(horizontal = 10.dp, vertical = 7.dp)
                        .border(1.dp, if (isDarkTheme) EmeraldPrimary.copy(alpha = 0.35f) else IndigoLight, HexagonChamferShape),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(3.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Language,
                            contentDescription = "Language",
                            tint = if (isDarkTheme) HolographicAqua else EmeraldPrimary,
                            modifier = Modifier.size(20.dp)
                        )
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Languages",
                            tint = if (isDarkTheme) Color.White else IndigoSecondary,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                DropdownMenu(
                    expanded = langMenuExpanded,
                    onDismissRequest = { langMenuExpanded = false },
                    modifier = Modifier.glassEffect(
                        shape = RoundedCornerShape(16.dp),
                        backgroundColor = if (isDarkTheme) CardBgDark.copy(alpha = 0.85f) else Color.White.copy(alpha = 0.88f),
                        borderColor = Color.White.copy(alpha = 0.35f)
                    )
                ) {
                    val menuItemColor = if (isDarkTheme) Color.White else Color.DarkGray
                    DropdownMenuItem(
                        text = { Text("🇬🇧 English", color = menuItemColor, fontSize = 13.sp) },
                        onClick = {
                            viewModel.setLanguage("en")
                            langMenuExpanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("🇪🇹 አማርኛ (Amharic)", color = menuItemColor, fontSize = 13.sp) },
                        onClick = {
                            viewModel.setLanguage("am")
                            langMenuExpanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("🇪🇹 Afaan Oromoo (Oromifa)", color = menuItemColor, fontSize = 13.sp) },
                        onClick = {
                            viewModel.setLanguage("om")
                            langMenuExpanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("🇪🇹 Soomaali (Somali)", color = menuItemColor, fontSize = 13.sp) },
                        onClick = {
                            viewModel.setLanguage("so")
                            langMenuExpanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("🇪🇹 ትግርኛ (Tigrigna)", color = menuItemColor, fontSize = 13.sp) },
                        onClick = {
                            viewModel.setLanguage("ti")
                            langMenuExpanded = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun SubjectHexCard(
    subjectName: String,
    iconName: String,
    isCompleted: Boolean,
    isLocked: Boolean = false,
    semesterBadge: String? = null,
    progress: Float = 0f,
    isDarkTheme: Boolean = false,
    currentLang: String = "en",
    onSubjectClick: () -> Unit
) {
    fun t(key: String): String = TranslationManager.get(key, currentLang)

    val isEffectivelyCompleted = isCompleted || progress >= 0.999f

    val cardBg = if (isDarkTheme) {
        if (isLocked) Color(0xFF161E30).copy(alpha = 0.65f) else CardBgDark
    } else {
        if (isLocked) Color(0xFFF8FAFC) else Color.White
    }

    val cardBorderColor = if (isDarkTheme) {
        if (isLocked) Color(0xFF334155).copy(alpha = 0.5f)
        else if (isEffectivelyCompleted) EmeraldPrimary.copy(alpha = 0.8f)
        else Color(0xFF334155).copy(alpha = 0.7f)
    } else {
        if (isLocked) Color(0xFFE2E8F0)
        else if (isEffectivelyCompleted) EmeraldPrimary
        else Color(0xFFE2E8F0)
    }

    val iconBoxBg = if (isDarkTheme) {
        if (isLocked) Color(0xFF3B1818).copy(alpha = 0.6f)
        else if (isEffectivelyCompleted) Color(0xFF064E3B).copy(alpha = 0.8f)
        else Color(0xFF0F172A)
    } else {
        if (isLocked) Color(0xFFFEF2F2)
        else if (isEffectivelyCompleted) Color(0xFFECFDF5)
        else Color(0xFFEEF2FF)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(184.dp)
            .pressBounce(pressedScale = 0.96f)
            .clickable(onClick = onSubjectClick)
            .testTag("subject_card_${iconName}"),
        shape = RoundedCornerShape(22.dp),
        border = BorderStroke(width = if (isEffectivelyCompleted) 1.5.dp else 1.dp, color = cardBorderColor),
        colors = CardDefaults.cardColors(
            containerColor = cardBg
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isDarkTheme) 6.dp else 3.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    if (isDarkTheme && !isLocked) {
                        Brush.verticalGradient(
                            colors = listOf(
                                CardBgDark,
                                Color(0xFF0F172A).copy(alpha = 0.95f)
                            )
                        )
                    } else {
                        Brush.linearGradient(listOf(cardBg, cardBg))
                    }
                )
        ) {
            // Semester Badge pill in Top Left Corner
            if (!semesterBadge.isNullOrEmpty()) {
                Surface(
                    shape = RoundedCornerShape(topStart = 16.dp, bottomEnd = 10.dp),
                    color = if (semesterBadge.contains("1")) EmeraldPrimary.copy(alpha = 0.9f) else Color(0xFF3B82F6).copy(alpha = 0.9f),
                    modifier = Modifier.align(Alignment.TopStart)
                ) {
                    Text(
                        text = semesterBadge,
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Black,
                            fontSize = 9.sp
                        ),
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 7.dp, vertical = 3.dp)
                    )
                }
            }

            // Circular Progress Bar in Top Right Corner showing material usage
            SubjectProgressIndicator(
                progress = progress,
                isCompleted = isCompleted,
                isLocked = isLocked,
                isDarkTheme = isDarkTheme,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 10.dp, end = 10.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp, vertical = 14.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Icon Box
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .clip(HexagonChamferShape)
                        .background(iconBoxBg),
                    contentAlignment = Alignment.Center
                ) {
                    DuotoneIcon(
                        name = if (isLocked) "lock" else iconName,
                        isActive = !isLocked && isEffectivelyCompleted,
                        modifier = Modifier.size(30.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(38.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = subjectName,
                        style = MaterialTheme.typography.displaySmall.copy(
                            fontWeight = FontWeight.Black,
                            fontSize = 12.sp,
                            lineHeight = 16.sp,
                            letterSpacing = (-0.1).sp
                        ),
                        color = if (isLocked) Color.Gray else if (isDarkTheme) Color.White else IndigoSecondary,
                        textAlign = TextAlign.Center,
                        maxLines = 2,
                        overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                // Completed / Play / Locked guide indicators
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(18.dp),
                    contentAlignment = Alignment.Center
                ) {
                    if (isLocked) {
                        Text(
                            text = t("badge_locked_trial"),
                            color = Color(0xFFEF4444),
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Black,
                                fontSize = 10.sp
                            ),
                            maxLines = 1,
                            overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                        )
                    } else if (isEffectivelyCompleted) {
                        Text(
                            text = t("badge_completed"),
                            color = if (isDarkTheme) HolographicAqua else EmeraldDark,
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Black,
                                fontSize = 10.sp
                            ),
                            maxLines = 1,
                            overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                        )
                    } else {
                        Text(
                            text = t("badge_tap_to_study"),
                            color = if (isDarkTheme) TextMuted else Color.Gray,
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 10.sp
                            ),
                            maxLines = 1,
                            overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SubjectProgressIndicator(
    progress: Float,
    isCompleted: Boolean,
    isLocked: Boolean,
    isDarkTheme: Boolean,
    modifier: Modifier = Modifier
) {
    val animatedProgress by animateFloatAsState(
        targetValue = if (isCompleted) 1f else progress.coerceIn(0f, 1f),
        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing),
        label = "subject_progress_anim"
    )

    val progressPercent = (animatedProgress * 100).toInt()
    val trackColor = if (isDarkTheme) Color.White.copy(alpha = 0.12f) else Color(0xFFE2E8F0)
    val progressColor = when {
        isLocked -> Color(0xFF94A3B8)
        isCompleted || animatedProgress >= 0.999f -> EmeraldPrimary
        animatedProgress > 0.5f -> HolographicAqua
        animatedProgress > 0.1f -> GoldDark
        else -> EmeraldPrimary
    }

    Box(
        modifier = modifier
            .size(32.dp)
            .testTag("subject_progress_ring"),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            progress = { if (isLocked) 0f else animatedProgress },
            modifier = Modifier.fillMaxSize(),
            color = progressColor,
            strokeWidth = 2.5.dp,
            trackColor = trackColor,
            strokeCap = StrokeCap.Round
        )

        if (isLocked) {
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = "Locked",
                tint = Color(0xFFEF4444),
                modifier = Modifier.size(11.dp)
            )
        } else if (isCompleted || animatedProgress >= 0.999f) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Completed",
                tint = EmeraldPrimary,
                modifier = Modifier.size(12.dp)
            )
        } else {
            Text(
                text = "$progressPercent%",
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Black,
                    fontSize = if (progressPercent == 100) 8.sp else 8.5.sp
                ),
                color = if (isDarkTheme) Color.White else IndigoSecondary
            )
        }
    }
}
