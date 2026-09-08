package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.StudyViewModel
import com.example.ui.components.*
import com.example.ui.theme.*

data class TextbookUnit(
    val unitNumber: String,
    val title: String,
    val pageStart: Int,
    val summary: String,
    val keyTopics: List<String>
)

data class TextbookEdition(
    val grade: String,
    val title: String,
    val fileName: String,
    val pageCount: Int,
    val publisher: String = "Ministry of Education Ethiopia (MoE)",
    val curriculumVersion: String = "New Curriculum (FDRE-MoE)",
    val units: List<TextbookUnit>
)

object OfficialTextbookRegistry {
    fun getTextbooksForSubject(subjectName: String): List<TextbookEdition> {
        val lower = subjectName.lowercase()
        return when {
            lower.contains("bio") -> listOf(
                TextbookEdition(
                    grade = "Grade 12",
                    title = "Grade 12 Biology Student Textbook",
                    fileName = "Grade_12_Biology.pdf",
                    pageCount = 358,
                    units = listOf(
                        TextbookUnit("Unit 1", "Application of Biology", 1, "Biotechnology, microbial genetics, recombinant DNA, industrial fermentation, and bioethics in Ethiopia.", listOf("Recombinant DNA", "Biotechnology in Agriculture", "Fermentation & Bioethics")),
                        TextbookUnit("Unit 2", "Microorganisms", 54, "Classification of bacteria, viruses, fungi, protozoa, microbiological techniques, and microbial diseases.", listOf("Bacteria & Viruses", "Culturing Techniques", "Pathogenic Microbes")),
                        TextbookUnit("Unit 3", "Energy Transformation", 112, "Photosynthesis light/dark reactions, cellular respiration, glycolysis, Krebs cycle, and ATP synthesis.", listOf("Light-dependent Reactions", "Calvin Cycle", "Cellular Respiration & ATP")),
                        TextbookUnit("Unit 4", "Evolution", 180, "Theories of origin of life, Lamarckism, Darwinian natural selection, speciation, and hominid evolution in Ethiopia (Lucy/Ardi).", listOf("Darwinian Natural Selection", "Speciation", "Human Evolution in Ethiopia")),
                        TextbookUnit("Unit 5", "Human Body Systems & Health", 240, "Nervous coordination, endocrine glands, homeostasis, thermoregulation, and excretion mechanisms.", listOf("Action Potentials & Synapses", "Endocrine Hormones", "Homeostasis & Kidney Function"))
                    )
                ),
                TextbookEdition(
                    grade = "Grade 11",
                    title = "Grade 11 Biology Student Textbook",
                    fileName = "Grade_11_Biology.pdf",
                    pageCount = 294,
                    units = listOf(
                        TextbookUnit("Unit 1", "Biology and Technology", 1, "Biomimicry, nature-inspired technologies, biotechnology tools, and bioinformatics fundamentals.", listOf("Learning from Nature", "Biomimicry Examples", "Modern Bio-tools")),
                        TextbookUnit("Unit 2", "Cell Biology and Enzymology", 42, "Cell membrane dynamics, organelle functions, enzyme kinetics, and metabolic pathways.", listOf("Fluid Mosaic Model", "Enzyme Activation Energy", "Allosteric Regulation")),
                        TextbookUnit("Unit 3", "Plant Biology", 102, "Plant tissues, xylem/phloem transport mechanisms, transpiration pull, and plant hormones.", listOf("Water Transport & Stomata", "Phloem Translocation", "Auxins & Gibberellins")),
                        TextbookUnit("Unit 4", "Genetics & Inheritance", 146, "Mendelian inheritance, sex linkage, blood group genetics, pedigree analysis, and gene therapy.", listOf("Monohybrid & Dihybrid", "Sex-linked Traits", "Pedigree Analysis & Gene Therapy")),
                        TextbookUnit("Unit 5", "The Human Body Systems", 180, "Musculoskeletal mechanics, human reproductive physiology, gametogenesis, and STIs epidemiology.", listOf("Sliding Filament Theory", "Reproductive Hormones", "Contraceptives & STIs")),
                        TextbookUnit("Unit 6", "Population & Natural Resources", 236, "Population ecology dynamics, carrying capacity, resource conservation, and biodiversity in Ethiopia.", listOf("Logistic Growth Models", "Renewable vs Non-renewable", "Ethiopian Biodiversity Hotspots"))
                    )
                ),
                TextbookEdition(
                    grade = "Grade 10",
                    title = "Grade 10 Biology Student Textbook",
                    fileName = "Grade_10_Biology.pdf",
                    pageCount = 177,
                    units = listOf(
                        TextbookUnit("Unit 1", "Sub-Fields of Biology", 1, "Branches of biological sciences, scientific method, and contributions to society.", listOf("Branches of Biology", "Scientific Method", "Ethiopian Biologists")),
                        TextbookUnit("Unit 2", "Plants", 28, "Structure and function of roots, stems, leaves, flowers, pollination, and seed dispersal.", listOf("Plant Morphology", "Photosynthesis Basics", "Reproduction in Plants")),
                        TextbookUnit("Unit 3", "Biochemical Molecules", 65, "Carbohydrates, lipids, proteins, nucleic acids, and dietary requirements.", listOf("Carbohydrates & Lipids", "Proteins & Enzymes", "Nucleic Acids")),
                        TextbookUnit("Unit 4", "Cell Division", 110, "Cell cycle stages, mitosis phases, meiosis, genetic variation, and cancer biology.", listOf("Mitosis Phases", "Meiosis & Crossing Over", "Cancer & Mutation"))
                    )
                )
            )

            lower.contains("chem") -> listOf(
                TextbookEdition(
                    grade = "Grade 12",
                    title = "Grade 12 Chemistry Student Textbook",
                    fileName = "Grade_12_Chemistry.pdf",
                    pageCount = 298,
                    units = listOf(
                        TextbookUnit("Unit 1", "Acid-Base Equilibria", 1, "Arrhenius, Bronsted-Lowry, Lewis theories, pH calculations, buffer solutions, and salt hydrolysis.", listOf("pH & pOH Calculations", "Buffer Capacity & Action", "Acid-Base Titration Curves")),
                        TextbookUnit("Unit 2", "Electrochemistry", 78, "Galvanic cells, Nernst equation, standard electrode potentials, electrolysis, and Faraday's laws.", listOf("Standard Electrode Potentials", "Galvanic & Electrolytic Cells", "Faraday's Quantitative Laws")),
                        TextbookUnit("Unit 3", "Industrial Chemistry", 160, "Haber-Bosch process, Contact process, Solvay process, fertilizers, metallurgy in Ethiopia.", listOf("Ammonia & Nitric Acid", "Sulfuric Acid Manufacture", "Fertilizers & Sugar Industry")),
                        TextbookUnit("Unit 4", "Polymers", 235, "Addition and condensation polymerization, synthetic plastics, natural rubber, and recycling.", listOf("Addition Polymerization", "Condensation Polymers", "Biodegradable Polymers"))
                    )
                ),
                TextbookEdition(
                    grade = "Grade 11",
                    title = "Grade 11 Chemistry Student Textbook",
                    fileName = "Grade_11_Chemistry.pdf",
                    pageCount = 342,
                    units = listOf(
                        TextbookUnit("Unit 1", "Atomic Structure & Periodic Table", 1, "Quantum mechanical model, electronic configurations, periodic trends, and spectral lines.", listOf("Quantum Numbers", "Aufbau & Hund's Rules", "Periodic Properties")),
                        TextbookUnit("Unit 2", "Chemical Bonding", 68, "Ionic, covalent, metallic bonding, Lewis structures, VSEPR theory, hybridization, intermolecular forces.", listOf("VSEPR Geometry", "Hybridization (sp, sp2, sp3)", "Hydrogen Bonding")),
                        TextbookUnit("Unit 3", "Physical States of Matter", 148, "Ideal gas law, Dalton's law, Graham's law, kinetic molecular theory, liquids and solids.", listOf("Gas Laws & Ideal Gas Equation", "Kinetic Molecular Theory", "Phase Diagrams")),
                        TextbookUnit("Unit 4", "Chemical Kinetics", 215, "Reaction rates, rate laws, order of reaction, Arrhenius equation, catalysis.", listOf("Rate Laws & Half-Life", "Activation Energy", "Catalysts & Collision Theory")),
                        TextbookUnit("Unit 5", "Chemical Equilibrium", 270, "Law of mass action, Kc and Kp calculations, Le Chatelier's principle, solubility product (Ksp).", listOf("Equilibrium Constant Calculations", "Le Chatelier's Shifts", "Common Ion Effect & Ksp"))
                    )
                ),
                TextbookEdition(
                    grade = "Grade 10",
                    title = "Grade 10 Chemistry Student Textbook",
                    fileName = "Grade_10_Chemistry.pdf",
                    pageCount = 306,
                    units = listOf(
                        TextbookUnit("Unit 1", "Chemical Reactions and Stoichiometry", 1, "Balancing redox reactions, mole concept, limiting reactants, percent yield.", listOf("Mole Concept & Avogadro", "Limiting Reactant", "Percent Yield")),
                        TextbookUnit("Unit 2", "Solutions", 82, "Types of solutions, solubility curves, concentration units (Molarity, Molality, PPM).", listOf("Molarity & Dilutions", "Solubility Factors", "Colligative Properties")),
                        TextbookUnit("Unit 3", "Important Inorganic Compounds", 154, "Oxides, acids, bases, and salts: nomenclature, preparation, and properties.", listOf("Acidic & Basic Oxides", "Properties of Salts", "Precipitation Reactions")),
                        TextbookUnit("Unit 4", "Introduction to Organic Chemistry", 220, "Hydrocarbons: alkanes, alkenes, alkynes, isomerism, functional groups nomenclature.", listOf("IUPAC Nomenclature", "Structural Isomerism", "Functional Groups"))
                    )
                ),
                TextbookEdition(
                    grade = "Grade 9",
                    title = "Grade 9 Chemistry Student Textbook",
                    fileName = "Grade_9_Chemistry.pdf",
                    pageCount = 183,
                    units = listOf(
                        TextbookUnit("Unit 1", "Chemistry and Its Importance", 1, "Nature of chemistry, lab safety, scientific inquiry in chemical discoveries.", listOf("Scope of Chemistry", "Lab Equipment Safety", "Measurement Units")),
                        TextbookUnit("Unit 2", "Measurements and Units", 36, "SI units, metric prefixes, dimensional analysis, significant figures in chemistry.", listOf("SI Base Units", "Significant Figures", "Density Calculations")),
                        TextbookUnit("Unit 3", "Structure of the Atom", 74, "Subatomic particles, isotopes, atomic number, relative atomic mass.", listOf("Protons, Neutrons, Electrons", "Isotopes & Atomic Mass", "Rutherford Model")),
                        TextbookUnit("Unit 4", "Periodic Classification of Elements", 120, "Historical development of periodic table, periods, groups, metals, non-metals.", listOf("Mendeleev's Table", "Modern Periodic Law", "Valence Electrons"))
                    )
                )
            )

            lower.contains("phys") -> listOf(
                TextbookEdition(
                    grade = "Grade 12",
                    title = "Grade 12 Physics Student Textbook",
                    fileName = "Grade_12_Physics.pdf",
                    pageCount = 186,
                    units = listOf(
                        TextbookUnit("Unit 1", "Thermodynamics", 1, "Heat engines, Carnot cycle, 1st and 2nd laws of thermodynamics, entropy, PV diagrams.", listOf("First Law of Thermodynamics", "Heat Engines & Carnot Efficiency", "Entropy & Reversibility")),
                        TextbookUnit("Unit 2", "Oscillations and Waves", 45, "Simple harmonic motion, wave equations, sound speed, Doppler effect, resonance.", listOf("SHM Mathematics", "Wave Interference & Superposition", "Doppler Effect")),
                        TextbookUnit("Unit 3", "Wave Optics", 92, "Huygens principle, Young's double slit interference, diffraction gratings, polarization.", listOf("Young's Double-Slit Experiment", "Diffraction Patterns", "Brewster's Law")),
                        TextbookUnit("Unit 4", "Modern Physics", 135, "Photoelectric effect, de Broglie wavelength, Bohr model, nuclear binding energy, radioactivity.", listOf("Photoelectric Equation", "Wave-Particle Duality", "Nuclear Fission & Fusion"))
                    )
                ),
                TextbookEdition(
                    grade = "Grade 11",
                    title = "Grade 11 Physics Student Textbook",
                    fileName = "Grade_11_Physics.pdf",
                    pageCount = 338,
                    units = listOf(
                        TextbookUnit("Unit 1", "Physics and Human Society", 1, "Significance of physics, experimental measurement techniques, precision vs accuracy.", listOf("Physics Innovations", "Error Analysis", "Dimensional Consistency")),
                        TextbookUnit("Unit 2", "Vectors", 34, "Vector addition, resolution of components, dot product, cross product in 2D and 3D.", listOf("Vector Resolution", "Scalar (Dot) Product", "Vector (Cross) Product")),
                        TextbookUnit("Unit 3", "2D Motion & Projectiles", 85, "Projectile motion formulas, circular motion kinematics, relative velocity.", listOf("Projectile Trajectory Formulas", "Centripetal Acceleration", "Relative Velocity")),
                        TextbookUnit("Unit 4", "Dynamics & Newton's Laws", 142, "Newton's laws of motion, friction, impulse, linear momentum conservation, collisions.", listOf("Newton's 3 Laws Applications", "Frictional Forces", "Elastic & Inelastic Collisions")),
                        TextbookUnit("Unit 5", "Work, Energy, and Power", 205, "Work-energy theorem, conservative forces, potential energy curves, power efficiency.", listOf("Work-Kinetic Energy Theorem", "Conservation of Mechanical Energy", "Power Output")),
                        TextbookUnit("Unit 6", "Rotational Dynamics", 260, "Torque, moment of inertia, angular momentum conservation, rotational kinetic energy.", listOf("Torque Calculations", "Moment of Inertia Theorems", "Conservation of Angular Momentum"))
                    )
                ),
                TextbookEdition(
                    grade = "Grade 10",
                    title = "Grade 10 Physics Student Textbook",
                    fileName = "Grade_10_Physics.pdf",
                    pageCount = 258,
                    units = listOf(
                        TextbookUnit("Unit 1", "Motion in One Dimension", 1, "Uniform motion, accelerated motion equations, graphs of motion, free fall.", listOf("Kinematic Equations", "v-t & s-t Graphs", "Free Fall Acceleration")),
                        TextbookUnit("Unit 2", "Forces and Newton's Laws", 52, "Inertia, force vectors, mass vs weight, equilibrium of forces.", listOf("Newton's First Law", "F = ma Calculations", "Equilibrium Conditions")),
                        TextbookUnit("Unit 3", "Circular Motion & Gravitation", 108, "Uniform circular motion, Newton's universal law of gravitation, planetary orbits.", listOf("Centripetal Force", "Universal Gravitation Formula", "Kepler's Planetary Laws")),
                        TextbookUnit("Unit 4", "Electrostatics", 164, "Coulomb's law, electric field lines, electric potential, capacitors in series and parallel.", listOf("Coulomb's Law", "Electric Field & Potential", "Capacitor Energy Storage")),
                        TextbookUnit("Unit 5", "Current Electricity", 212, "Ohm's law, resistivity, Kirchhoff's rules, electrical power and energy.", listOf("Resistors in Series & Parallel", "Kirchhoff's Junction & Loop Rules", "Electric Energy Costs"))
                    )
                )
            )

            lower.contains("math") -> listOf(
                TextbookEdition(
                    grade = "Grade 12",
                    title = "Grade 12 Mathematics Student Textbook",
                    fileName = "Grade_12_Mathematics.pdf",
                    pageCount = 426,
                    units = listOf(
                        TextbookUnit("Unit 1", "Sequences and Series", 1, "Arithmetic and geometric progressions, sigma notation, convergence, financial math.", listOf("AP & GP Formulas", "Infinite Geometric Series", "Compound Interest & Annuity")),
                        TextbookUnit("Unit 2", "Introduction to Calculus (Limits & Derivatives)", 75, "Limits evaluation, continuity, derivative rules, tangent lines, optimization.", listOf("Limits of Functions", "Product, Quotient, Chain Rules", "Extrema & Optimization Problems")),
                        TextbookUnit("Unit 3", "Integrals and Applications", 162, "Indefinite and definite integrals, substitution method, area under curve, differential equations.", listOf("Fundamental Theorem of Calculus", "Integration by Substitution", "Area Between Curves")),
                        TextbookUnit("Unit 4", "Probability and Statistics", 240, "Permutations, combinations, conditional probability, Bayes' theorem, normal distribution.", listOf("Combinatorics", "Conditional Probability & Bayes", "Normal Distribution & Z-scores")),
                        TextbookUnit("Unit 5", "Matrices and Determinants", 330, "Matrix operations, determinants up to 3x3, Cramer's rule, inverse matrix, systems of equations.", listOf("Matrix Multiplication", "Determinants Evaluation", "Cramer's Rule for 3x3 Systems"))
                    )
                ),
                TextbookEdition(
                    grade = "Grade 11",
                    title = "Grade 11 Mathematics Student Textbook",
                    fileName = "Grade_11_Mathematics.pdf",
                    pageCount = 494,
                    units = listOf(
                        TextbookUnit("Unit 1", "Relations and Functions", 1, "Domain, range, composite functions, inverse functions, composition algebra.", listOf("Function Domains & Inverses", "Composite Function Algebra", "Transformations of Graphs")),
                        TextbookUnit("Unit 2", "Rational Expressions & Rational Functions", 72, "Simplifying rational expressions, partial fractions, asymptotes, graphing rational functions.", listOf("Partial Fractions Decomposition", "Vertical & Horizontal Asymptotes", "Graphing Rational Curves")),
                        TextbookUnit("Unit 3", "Coordinate Geometry & Conic Sections", 155, "Lines, circles, parabolas, ellipses, hyperbolas equations and focus/directrix properties.", listOf("Equations of Circles", "Parabolas, Ellipses, Hyperbolas", "Eccentricity & Directrix")),
                        TextbookUnit("Unit 4", "Trigonometric Functions", 250, "Unit circle, radian measure, trig identities, compound angles, double angle formulas.", listOf("Unit Circle Values", "Double Angle & Half Angle Formulas", "Trigonometric Equations Solving")),
                        TextbookUnit("Unit 5", "Vectors in Two Dimensions", 345, "Components, scalar product, angle between vectors, vector projection.", listOf("Vector Dot Product", "Angle Between Two Vectors", "Vector Projections")),
                        TextbookUnit("Unit 6", "Statistics", 410, "Measures of central tendency, variance, standard deviation, grouped data analysis.", listOf("Mean, Median, Mode", "Variance & Standard Deviation", "Quartiles & Box Plots"))
                    )
                ),
                TextbookEdition(
                    grade = "Grade 10",
                    title = "Grade 10 Mathematics Student Textbook",
                    fileName = "Grade_10_Mathematics.pdf",
                    pageCount = 394,
                    units = listOf(
                        TextbookUnit("Unit 1", "Relations and Functions", 1, "Definitions of relations, domain, range, function mapping.", listOf("Domain & Range", "One-to-one Functions", "Function Notation")),
                        TextbookUnit("Unit 2", "Polynomial Functions", 67, "Quadratic equations, remainder theorem, factor theorem, roots of polynomial equations.", listOf("Remainder & Factor Theorems", "Synthetic Division", "Quadratic Formula")),
                        TextbookUnit("Unit 3", "Exponential and Logarithmic Functions", 121, "Laws of exponents, logarithms properties, exponential decay and growth equations.", listOf("Laws of Exponents", "Properties of Logarithms", "Exponential Equations")),
                        TextbookUnit("Unit 4", "Trigonometric Functions", 199, "Sine, cosine, tangent ratios in right triangles, angle of elevation and depression.", listOf("SOH CAH TOA Ratios", "Angles of Elevation/Depression", "Trig Identities Basics"))
                    )
                )
            )

            lower.contains("eng") -> listOf(
                TextbookEdition(
                    grade = "Grade 12",
                    title = "Grade 12 English Student Textbook",
                    fileName = "Grade_12_English.pdf",
                    pageCount = 270,
                    units = listOf(
                        TextbookUnit("Unit 1", "Sustainable Development", 1, "Reading comprehension, environmental vocabulary, advanced passive voice.", listOf("Reading on Sustainability", "Passive Voice Mastery", "Essay Writing Structure")),
                        TextbookUnit("Unit 2", "Time Management & Goal Setting", 30, "Conditional sentences (Types 1, 2, 3, Mixed), vocabulary for scheduling and prioritization.", listOf("Conditional Clauses", "Time Management Idioms", "Formal Email Writing")),
                        TextbookUnit("Unit 3", "Evidence on Road Traffic Safety", 58, "Modal verbs of necessity and deduction, data interpretation from tables and bar charts.", listOf("Modal Verbs (Must, Should, Could)", "Analyzing Statistical Tables", "Cause & Effect Connectors")),
                        TextbookUnit("Unit 4", "Natural Resource Management", 88, "Relative clauses (defining vs non-defining), vocabulary on minerals and forestry.", listOf("Defining vs Non-defining Clauses", "Synthesizing Reading Texts", "Persuasive Argumentation")),
                        TextbookUnit("Unit 5", "Mechanization in Agriculture", 120, "Reported speech statements and questions, agricultural terminology in Ethiopian context.", listOf("Reported Speech Rules", "Tense Shifts in Indirect Speech", "Summary Writing"))
                    )
                ),
                TextbookEdition(
                    grade = "Grade 11",
                    title = "Grade 11 English Student Textbook",
                    fileName = "Grade_11_English.pdf",
                    pageCount = 290,
                    units = listOf(
                        TextbookUnit("Unit 1", "The Physical World", 1, "Descriptive writing, adjectives order, vocabulary on geography and climate.", listOf("Adjective Order Rules", "Descriptive Essays", "Vocabulary in Context")),
                        TextbookUnit("Unit 2", "Cultural Heritage of Ethiopia", 32, "Past simple vs past perfect, narrative structures, indigenous traditions.", listOf("Past Narrative Tenses", "Phrasal Verbs", "Oral Literature Analysis")),
                        TextbookUnit("Unit 3", "Health and Wellbeing", 64, "Gerunds vs Infinitives, health terminology, expressing advice and warnings.", listOf("Gerunds and Infinitives", "Giving Medical Advice", "Argumentative Essays")),
                        TextbookUnit("Unit 4", "Indigenous Technologies", 98, "Connectors of contrast and concession (Although, Despite, However), traditional craft systems.", listOf("Concession Connectors", "Passive in Technical Descriptions", "Vocabulary on Metallurgy & Weaving"))
                    )
                ),
                TextbookEdition(
                    grade = "Grade 10",
                    title = "Grade 10 English Student Textbook",
                    fileName = "Grade_10_English.pdf",
                    pageCount = 326,
                    units = listOf(
                        TextbookUnit("Unit 1", "Education & Lifelong Learning", 1, "Present perfect tense vs past simple, classroom discussion vocabulary.", listOf("Present Perfect Tense", "Collocations", "Paragraph Writing")),
                        TextbookUnit("Unit 2", "Traditional Games & Sports", 38, "Comparative and superlative forms, vocabulary on athletic games in Ethiopia.", listOf("Comparatives & Superlatives", "Sports Vocabulary", "Speech Delivery")),
                        TextbookUnit("Unit 3", "Environmental Conservation", 76, "Future tenses (Will vs Going to vs Present Continuous), ecology reading texts.", listOf("Expressing Future Plans", "Environmental Vocabulary", "Letter of Application"))
                    )
                )
            )

            lower.contains("econ") -> listOf(
                TextbookEdition(
                    grade = "Grade 12",
                    title = "Grade 12 Economics Student Textbook",
                    fileName = "Grade_12_Economics.pdf",
                    pageCount = 214,
                    units = listOf(
                        TextbookUnit("Unit 1", "Fundamental Concepts of Macroeconomics", 1, "Macroeconomic goals, circular flow model, GDP, GNP, and national income metrics.", listOf("GDP vs GNP Measurements", "Circular Flow of Income", "Nominal vs Real GDP")),
                        TextbookUnit("Unit 2", "Aggregate Demand and Aggregate Supply", 45, "AD/AS model, classical vs Keynesian perspectives, fiscal and monetary policies.", listOf("AD & AS Curves", "Multiplier Effect", "Monetary vs Fiscal Policies")),
                        TextbookUnit("Unit 3", "Market Failure and Consumer Protection", 95, "Externalities, public goods, market power, government intervention policies in Ethiopia.", listOf("Negative & Positive Externalities", "Public Goods Non-rivalry", "Price Ceilings & Floors")),
                        TextbookUnit("Unit 4", "Tax Theory and Practice in Ethiopia", 145, "Direct vs indirect taxes, principles of taxation, Ethiopian tax structure (ERCA).", listOf("Progressive vs Regressive Taxes", "VAT & Customs Duties in Ethiopia", "Tax Incidence & Deadweight Loss"))
                    )
                ),
                TextbookEdition(
                    grade = "Grade 11",
                    title = "Grade 11 Economics Student Textbook",
                    fileName = "Grade_11_Economics.pdf",
                    pageCount = 202,
                    units = listOf(
                        TextbookUnit("Unit 1", "Theory of Consumer Behavior and Demand", 1, "Cardinal vs ordinal utility, indifference curve analysis, budget constraint line.", listOf("Marginal Utility Diminishing Law", "Indifference Curves Properties", "Consumer Equilibrium Condition")),
                        TextbookUnit("Unit 2", "Market Structure and Decision of Firms", 48, "Perfect competition, monopoly, monopolistic competition, oligopoly equilibrium.", listOf("Profit Maximization (MR=MC)", "Monopoly Deadweight Loss", "Game Theory & Cartels")),
                        TextbookUnit("Unit 3", "National Income Accounting", 98, "Expenditure, income, and output approaches, GDP deflator, cost of living index.", listOf("Expenditure Approach Components", "GDP Deflator vs CPI", "Limitations of GDP")),
                        TextbookUnit("Unit 4", "Consumption, Saving, and Investment", 152, "Keynesian consumption function, MPC, MPS, investment determinants in developing economies.", listOf("Marginal Propensity to Consume", "Investment Multiplier", "Determinants of Capital Formation"))
                    )
                ),
                TextbookEdition(
                    grade = "Grade 10",
                    title = "Grade 10 Economics Student Textbook",
                    fileName = "Grade_10_Economics.pdf",
                    pageCount = 190,
                    units = listOf(
                        TextbookUnit("Unit 1", "Theory of Consumer Behaviour", 1, "Wants, scarcity, choice, opportunity cost, and utility satisfaction principles.", listOf("Scarcity and Choice", "Opportunity Cost Calculation", "Utility Concepts")),
                        TextbookUnit("Unit 2", "Theories of Demand and Supply", 42, "Law of demand, shifts vs movements along curve, price elasticity of demand.", listOf("Determinants of Demand", "Price Elasticity Formula", "Market Equilibrium Equilibrium")),
                        TextbookUnit("Unit 3", "Theories of Production and Cost", 90, "Short-run production functions, law of diminishing returns, fixed vs variable costs.", listOf("Total, Marginal & Average Product", "Cost Curves (ATC, AVC, MC)", "Economies of Scale"))
                    )
                ),
                TextbookEdition(
                    grade = "Grade 9",
                    title = "Grade 9 Economics Student Textbook",
                    fileName = "Grade_9_Economics.pdf",
                    pageCount = 119,
                    units = listOf(
                        TextbookUnit("Unit 1", "Introduction to Economics", 1, "Definition of economics, micro vs macro, factors of production in economy.", listOf("Economic Resources", "Micro vs Macroeconomics", "Positive vs Normative Statements")),
                        TextbookUnit("Unit 2", "The Basic Economic Problems", 28, "What, how, and for whom to produce, economic systems (traditional, command, market, mixed).", listOf("Fundamental Economic Questions", "Command vs Market Systems", "Production Possibility Frontier")),
                        TextbookUnit("Unit 3", "Main Sectors of the Ethiopian Economy", 62, "Agriculture sector significance, industrial development, services sector contribution.", listOf("Ethiopian Agriculture Structure", "Industrialization Challenges", "Export Commodities"))
                    )
                )
            )

            lower.contains("geog") -> listOf(
                TextbookEdition(
                    grade = "Grade 12",
                    title = "Grade 12 Geography Student Textbook",
                    fileName = "Grade_12_Geography.pdf",
                    pageCount = 266,
                    units = listOf(
                        TextbookUnit("Unit 1", "Major Geological Processes of the Earth", 1, "Plate tectonics, faulting, folding, volcanism, earthquakes, and East African Rift Valley.", listOf("Plate Tectonics Theory", "East African Rift System", "Earthquake Epicenters & Waves")),
                        TextbookUnit("Unit 2", "Climate Change: Global and Local Dimensions", 60, "Greenhouse gases, global warming causes, impacts on Ethiopian agriculture and hydrology.", listOf("Greenhouse Effect Mechanics", "Mitigation vs Adaptation Strategies", "Drought Patterns in Horn of Africa")),
                        TextbookUnit("Unit 3", "Issues in Sustainable Resource Management", 125, "Water resource basins of Ethiopia, GERD, soil erosion control, wildlife parks conservation.", listOf("Major River Basins of Ethiopia", "Grand Ethiopian Renaissance Dam", "Soil Degradation Countermeasures")),
                        TextbookUnit("Unit 4", "Geographical Information Systems (GIS) and Remote Sensing", 195, "Components of GIS, spatial data models, raster vs vector, satellite imagery analysis.", listOf("GIS Spatial Data Models", "Remote Sensing Principles", "Map Projections & Scales"))
                    )
                ),
                TextbookEdition(
                    grade = "Grade 11",
                    title = "Grade 11 Geography Student Textbook",
                    fileName = "Grade_11_Geography.pdf",
                    pageCount = 254,
                    units = listOf(
                        TextbookUnit("Unit 1", "Formation of Continents and Oceans", 1, "Continental drift theory (Wegener), seafloor spreading, paleomagnetism, structural landforms.", listOf("Continental Drift Evidence", "Seafloor Spreading", "Morphology of Ocean Floors")),
                        TextbookUnit("Unit 2", "Natural Resources and Sustainable Utilization", 55, "Forests, mineral deposits, energy resources, sustainable development paradigms.", listOf("Forest Canopy Layers", "Mineral Resources Distribution", "Sustainable Development Goals")),
                        TextbookUnit("Unit 3", "Global Population Dynamics", 118, "Demographic transition model, population pyramids, migration push/pull factors.", listOf("Demographic Transition Stages", "Age-Sex Pyramids Interpretation", "International Migration Trends")),
                        TextbookUnit("Unit 4", "Economic Geography & Trade", 185, "Primary, secondary, tertiary activities, international trade balance, globalization.", listOf("Sectors of Economic Activity", "Terms of Trade", "Economic Regional Blocs"))
                    )
                ),
                TextbookEdition(
                    grade = "Grade 10",
                    title = "Grade 10 Geography Student Textbook",
                    fileName = "Grade_10_Geography.pdf",
                    pageCount = 214,
                    units = listOf(
                        TextbookUnit("Unit 1", "Landforms of Africa", 1, "Plateaus, mountains, rift valleys, drainage basins of the African continent.", listOf("Topography of Africa", "Major African Rivers (Nile, Congo)", "African Coastal Plains")),
                        TextbookUnit("Unit 2", "Climate of Africa", 52, "Air masses, ITCZ movement, rainfall regimes, vegetation zones in Africa.", listOf("ITCZ Seasonal Migration", "Equatorial to Desert Climates", "Vegetation Belts")),
                        TextbookUnit("Unit 3", "Natural Resource Base of Africa", 104, "Water, soils, mineral wealth of Africa, exploitation challenges.", listOf("Major Mineral Deposits", "Soil Conservation in Africa", "Hydroelectric Potential")),
                        TextbookUnit("Unit 4", "Population of Africa", 158, "Growth rates, distribution factors, urban growth, demographic challenges.", listOf("Population Growth Trends", "Urbanization in African Cities", "Dependency Ratio Issues"))
                    )
                ),
                TextbookEdition(
                    grade = "Grade 9",
                    title = "Grade 9 Geography Student Textbook",
                    fileName = "Grade_9_Geography.pdf",
                    pageCount = 237,
                    units = listOf(
                        TextbookUnit("Unit 1", "Geological History and Topography of Ethiopia", 1, "Precambrian, Mesozoic, Cenozoic eras geological events in Horn of Africa.", listOf("Geological Eras of Ethiopia", "Mesozoic Marine Transgression", "Tertiary Volcanism & Traps")),
                        TextbookUnit("Unit 2", "Climate of Ethiopia", 64, "Altitude influence on temperature, climatic zones (Bereha, Kolla, Woina Dega, Dega, Wurch).", listOf("Ethiopian Traditional Climate Zones", "Rainfall Seasons (Kiremt, Belg, Bega)", "Lapse Rate Calculations")),
                        TextbookUnit("Unit 3", "Drainage Systems of Ethiopia", 130, "Western, South-Eastern, and Rift Valley drainage systems, lakes of Ethiopia.", listOf("Abay, Tekeze, Baro Basins", "Rift Valley Lakes", "Water Tower of East Africa"))
                    )
                )
            )

            lower.contains("hist") -> listOf(
                TextbookEdition(
                    grade = "Grade 12",
                    title = "Grade 12 History Student Textbook",
                    fileName = "Grade_12_History.pdf",
                    pageCount = 290,
                    units = listOf(
                        TextbookUnit("Unit 1", "Ethiopian History: 1941 to 1991", 1, "Post-liberation restoration, Emperor Haile Selassie regime, the 1974 revolution, and the Derg era.", listOf("Restoration Period (1941-1974)", "1960 Coup d'Etat Attempt", "1974 Popular Revolution", "Derg Regime & Red Terror")),
                        TextbookUnit("Unit 2", "World History: The Cold War Era (1945–1991)", 75, "Origins of Cold War, NATO vs Warsaw Pact, proxy conflicts (Korea, Vietnam), decolonization of Africa.", listOf("Cold War Ideological Struggle", "Decolonization Movements in Africa", "Non-Aligned Movement")),
                        TextbookUnit("Unit 3", "Major Developments in Post-1991 World and Ethiopia", 160, "Collapse of Soviet Union, transition period in Ethiopia, adoption of 1995 FDRE Constitution.", listOf("Fall of Berlin Wall & USSR Collapse", "1991-1995 Transition in Ethiopia", "1995 FDRE Constitution Principles"))
                    )
                ),
                TextbookEdition(
                    grade = "Grade 11",
                    title = "Grade 11 History Student Textbook",
                    fileName = "Grade_11_History.pdf",
                    pageCount = 270,
                    units = listOf(
                        TextbookUnit("Unit 1", "History, Historiography, and Human Evolution", 1, "Sources of history, historical methodology, hominid evolution evidence from Ethiopian sites.", listOf("Primary vs Secondary Sources", "Afar & Omo Paleolithic Discoveries", "Prehistoric Stone Tool Ages")),
                        TextbookUnit("Unit 2", "Ancient Civilizations up to c. 500 AD", 52, "Mesopotamia, Egypt, Indus Valley, ancient Greece, Rome, Da'amat and Aksumite kingdom.", listOf("Aksumite Inscriptions & Coins", "Aksum's Red Sea Trade", "Introduction of Christianity & Islam")),
                        TextbookUnit("Unit 3", "Ethiopia and the Horn from Medieval to Modern (1270–1855)", 120, "Restoration of Solomonic dynasty, Christian-Muslim sultanates conflict, Ahmad Gragn wars, Gadaa system expansion.", listOf("Solomonic Dynastic Revival", "Wars of Ahmad Ibrahim (Gragn)", "Oromo Population Movements & Gadaa", "Zemene Mesafint (Era of Princes)"))
                    )
                ),
                TextbookEdition(
                    grade = "Grade 10",
                    title = "Grade 10 History Student Textbook",
                    fileName = "Grade_10_History.pdf",
                    pageCount = 246,
                    units = listOf(
                        TextbookUnit("Unit 1", "State Formation and Modernization in Ethiopia (1855–1913)", 1, "Tewodros II centralisation, Yohannes IV defense against foreign aggression, Menelik II expansion and Battle of Adwa (1896).", listOf("Tewodros II State-building", "Yohannes IV & Battle of Metemma", "Battle of Adwa 1896 Victory", "Creation of Modern Ethiopian Borders")),
                        TextbookUnit("Unit 2", "First World War and Its Aftermath (1914–1939)", 85, "Causes of WWI, Treaty of Versailles, rise of fascism and Nazism, League of Nations failure.", listOf("Causes of World War I", "Treaty of Versailles Impacts", "Rise of Mussolini and Hitler")),
                        TextbookUnit("Unit 3", "The Italian Fascist Aggression on Ethiopia (1935–1941)", 165, "Walwal incident, Italian invasion, patriotic resistance movement (Arbegnoch), British intervention and liberation.", listOf("Walwal Incident 1934", "Patriotic Resistance Movement", "1941 Liberation of Addis Ababa"))
                    )
                ),
                TextbookEdition(
                    grade = "Grade 9",
                    title = "Grade 9 History Student Textbook",
                    fileName = "Grade_9_History.pdf",
                    pageCount = 219,
                    units = listOf(
                        TextbookUnit("Unit 1", "Introduction to the Study of History", 1, "Meaning of history, chronology, eras, dating systems, significance of studying Ethiopian heritage.", listOf("Definition & Nature of History", "Dating Systems (BC/AD, BCE/CE)", "Value of Historical Heritage")),
                        TextbookUnit("Unit 2", "Human Evolution and the Emergence of Agriculture", 48, "Australopithecus afarensis, Homo erectus, Neolithic agricultural revolution in Africa.", listOf("Ethiopia Cradle of Humankind", "Neolithic Agricultural Revolution", "Domestication of Crops in Horn of Africa")),
                        TextbookUnit("Unit 3", "Ancient Civilizations of Africa", 110, "Kush/Meroe, Carthage, Axumite civilization, early trade networks across Sahara and Red Sea.", listOf("Kingdom of Kush and Meroe", "Ancient Red Sea Maritime Trade", "Early Ethiopian Stone Monuments"))
                    )
                )
            )

            else -> listOf(
                TextbookEdition(
                    grade = "General",
                    title = "$subjectName Official Curriculum",
                    fileName = "General_Curriculum.pdf",
                    pageCount = 200,
                    units = listOf(
                        TextbookUnit("Unit 1", "Core Foundations", 1, "Foundational principles and learning objectives according to Ministry of Education.", listOf("Core Objectives", "Essential Competencies", "Assessment Framework")),
                        TextbookUnit("Unit 2", "Advanced Applications", 60, "Higher-order problem solving and examination preparation strategies.", listOf("Case Studies", "Exam Questions Analysis", "Summary Review"))
                    )
                )
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OfficialTextbookScreen(
    subjectName: String,
    onClose: () -> Unit,
    viewModel: StudyViewModel? = null
) {
    val editions = remember(subjectName) { OfficialTextbookRegistry.getTextbooksForSubject(subjectName) }
    var selectedGrade by remember { mutableStateOf(editions.firstOrNull()?.grade ?: "Grade 12") }
    var selectedUnitIndex by remember { mutableStateOf<Int?>(null) }
    var searchQuery by remember { mutableStateOf("") }
    val context = LocalContext.current

    val currentEdition = editions.firstOrNull { it.grade == selectedGrade } ?: editions.firstOrNull()

    val filteredUnits = remember(currentEdition, searchQuery) {
        val allUnits = currentEdition?.units ?: emptyList()
        if (searchQuery.isBlank()) allUnits
        else allUnits.filter {
            it.title.contains(searchQuery, ignoreCase = true) ||
            it.unitNumber.contains(searchQuery, ignoreCase = true) ||
            it.summary.contains(searchQuery, ignoreCase = true) ||
            it.keyTopics.any { topic -> topic.contains(searchQuery, ignoreCase = true) }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0A0F1D))
            .windowInsetsPadding(WindowInsets.statusBars)
    ) {
        // 1. Premium Top Navigation Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF0F172A))
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(EmeraldPrimary.copy(alpha = 0.15f))
                        .border(1.dp, EmeraldPrimary.copy(alpha = 0.3f), RoundedCornerShape(10.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.MenuBook,
                        contentDescription = null,
                        tint = EmeraldPrimary,
                        modifier = Modifier.size(22.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "OFFICIAL TEXTBOOK",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Black,
                                letterSpacing = 1.2.sp
                            ),
                            color = GoldAccent
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = Color(0xFF1E293B)
                        ) {
                            Text(
                                text = "FDRE MoE",
                                color = Color.White,
                                style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp),
                                modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                            )
                        }
                    }
                    Text(
                        text = subjectName,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Black,
                            fontSize = 17.sp
                        ),
                        color = Color.White,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            IconButton(
                onClick = onClose,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color(0xFF1E293B))
            ) {
                Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.White)
            }
        }

        // 2. Grade Level Selector Tabs
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF0F172A).copy(alpha = 0.8f))
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            editions.forEach { edition ->
                val isSelected = edition.grade == selectedGrade
                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(10.dp))
                        .clickable {
                            selectedGrade = edition.grade
                            selectedUnitIndex = null
                        },
                    shape = RoundedCornerShape(10.dp),
                    color = if (isSelected) EmeraldPrimary else Color(0xFF1E293B),
                    border = BorderStroke(
                        1.dp,
                        if (isSelected) EmeraldPrimary else Color.White.copy(alpha = 0.1f)
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = edition.grade,
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = if (isSelected) FontWeight.Black else FontWeight.Bold
                            ),
                            color = if (isSelected) Color.White else Color.LightGray
                        )
                        Text(
                            text = "${edition.pageCount}p",
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp),
                            color = if (isSelected) Color.White.copy(alpha = 0.85f) else TextMuted
                        )
                    }
                }
            }
        }

        // 3. Search Bar for Units and Topics
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Search unit, chapter, or key topic...", color = TextMuted, fontSize = 13.sp) },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = EmeraldPrimary, modifier = Modifier.size(20.dp)) },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { searchQuery = "" }) {
                        Icon(Icons.Default.Clear, contentDescription = "Clear", tint = Color.Gray)
                    }
                }
            },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color(0xFF131C2E),
                unfocusedContainerColor = Color(0xFF131C2E),
                focusedBorderColor = EmeraldPrimary,
                unfocusedBorderColor = Color(0xFF334155),
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp)
        )

        // 4. Main Content: Book Overview & Unit Breakdown
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Book Information Card
            if (currentEdition != null) {
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, Brush.linearGradient(listOf(Color.White.copy(alpha = 0.3f), EmeraldPrimary.copy(alpha = 0.4f))), RoundedCornerShape(16.dp)),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF131C2E))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = currentEdition.title,
                                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Black),
                                        color = Color.White
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = "${currentEdition.publisher} • ${currentEdition.curriculumVersion}",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = TextMuted
                                    )
                                }
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = EmeraldPrimary.copy(alpha = 0.2f),
                                    border = BorderStroke(1.dp, EmeraldPrimary)
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(Icons.Default.Verified, contentDescription = null, tint = EmeraldPrimary, modifier = Modifier.size(14.dp))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("MoE Verified", color = Color.White, style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp, fontWeight = FontWeight.Bold))
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(14.dp))

                            // Action: Open in External PDF Viewer / Reader Intent
                            Button(
                                onClick = {
                                    try {
                                        val intent = Intent(Intent.ACTION_VIEW).apply {
                                            setDataAndType(Uri.parse("content://media/external/file"), "application/pdf")
                                            flags = Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_GRANT_READ_URI_PERMISSION
                                        }
                                        context.startActivity(Intent.createChooser(intent, "Open with PDF Reader"))
                                    } catch (e: Exception) {
                                        // Fallback alert
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(44.dp)
                                    .pressBounce(),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary)
                            ) {
                                Icon(Icons.Default.PictureAsPdf, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Read Official ${currentEdition.grade} PDF (${currentEdition.pageCount} Pages)",
                                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }

            // Section Header: Chapters & Units Breakdown
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, bottom = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "OFFICIAL CHAPTERS & UNITS",
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Black,
                            letterSpacing = 1.sp
                        ),
                        color = HolographicAqua
                    )
                    Text(
                        text = "${filteredUnits.size} Units",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = TextMuted
                    )
                }
            }

            // List of Official Units
            items(filteredUnits) { unit ->
                val isExpanded = selectedUnitIndex == filteredUnits.indexOf(unit)
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .clickable {
                            selectedUnitIndex = if (isExpanded) null else filteredUnits.indexOf(unit)
                        }
                        .border(
                            1.dp,
                            if (isExpanded) EmeraldPrimary else Color(0xFF334155),
                            RoundedCornerShape(14.dp)
                        ),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isExpanded) Color(0xFF1E293B) else Color(0xFF131C2E)
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.weight(1f)
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = EmeraldPrimary.copy(alpha = 0.2f),
                                    modifier = Modifier.size(36.dp),
                                    border = BorderStroke(1.dp, EmeraldPrimary.copy(alpha = 0.5f))
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Text(
                                            text = unit.unitNumber.filter { it.isDigit() }.ifEmpty { "1" },
                                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Black),
                                            color = EmeraldPrimary
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(
                                        text = unit.unitNumber.uppercase(),
                                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, letterSpacing = 1.sp),
                                        color = GoldAccent
                                    )
                                    Text(
                                        text = unit.title,
                                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                        color = Color.White,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            }

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = Color(0xFF0F172A)
                                ) {
                                    Text(
                                        text = "Page ${unit.pageStart}",
                                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                                        color = TextMuted,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(6.dp))
                                Icon(
                                    imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                    contentDescription = null,
                                    tint = Color.LightGray
                                )
                            }
                        }

                        // Expanded unit details
                        AnimatedVisibility(visible = isExpanded) {
                            Column(modifier = Modifier.padding(top = 14.dp)) {
                                HorizontalDivider(color = Color(0xFF334155).copy(alpha = 0.6f))
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    text = "CHAPTER SUMMARY",
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Black),
                                    color = HolographicAqua
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = unit.summary,
                                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 13.sp, lineHeight = 20.sp),
                                    color = Color(0xFFCBD5E1)
                                )

                                Spacer(modifier = Modifier.height(12.dp))
                                Text(
                                    text = "CORE EUEE EXAMINATION TOPICS",
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Black),
                                    color = GoldAccent
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                unit.keyTopics.forEach { topic ->
                                    Row(
                                        modifier = Modifier.padding(vertical = 2.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            Icons.Default.CheckCircle,
                                            contentDescription = null,
                                            tint = EmeraldPrimary,
                                            modifier = Modifier.size(14.dp)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = topic,
                                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
                                            color = Color.White
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(14.dp))
                                OutlinedButton(
                                    onClick = {
                                        // Filter syllabus notes to this unit directly
                                        if (viewModel != null) {
                                            viewModel.selectedGradeFilter.value = currentEdition?.grade ?: "Grade 12"
                                            viewModel.startNotes()
                                        }
                                        onClose()
                                    },
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(10.dp),
                                    colors = ButtonDefaults.outlinedButtonColors(contentColor = EmeraldPrimary),
                                    border = BorderStroke(1.dp, EmeraldPrimary)
                                ) {
                                    Icon(Icons.Default.MenuBook, contentDescription = null, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Open Syllabus Notes for this Unit", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
