package com.example.data

object Grade11BiologyNotes {

    fun getGrade11BiologyNotes(): List<SubjectNote> {
        val notesList = mutableListOf<SubjectNote>()
        val subId = "euee_nat_bio"
        var idx = 1

        fun addNote(unit: String, title: String, content: String) {
            notesList.add(
                SubjectNote(
                    id = "g11_bio_note_${idx}",
                    subjectId = subId,
                    unit = unit,
                    title = title,
                    content = content,
                    gradeLevel = "Grade 11"
                )
            )
            idx++
        }

        // Unit 1
        addNote(
            "Unit 1 - Biology and Technology",
            "Learning from Nature, Technology and Bioethics (Sections 1.1 - 1.2)",
            """
            • Learning from Nature: Organisms possess well-adapted structures and forms shaped by millions of years of evolution, which inspire scientists and engineers to develop functional and applicable mechanisms for various technological systems through biomimicry.
            • Biomimetics (Biomimicry): The practice of learning from and mimicking strategies, shapes, materials, and functional mechanisms found in biological nature to design efficient technologies at macro and nano scales.
            • Bioethics: The study of ethical issues arising from biological research and technological advancements, addressing concerns in genetic engineering, biotechnology, animal testing, and environmental management.
            """.trimIndent()
        )

        // Unit 2
        addNote(
            "Unit 2 - Animal Function",
            "Animal Nutrition, Respiration, Circulation, Excretion, and Homeostasis (Sections 2.1 - 2.7)",
            """
            • Animal Nutrition and Digestion: Ingestion, mechanical and chemical digestion, absorption, and egestion across various animal groups, highlighting adaptive digestive structures.
            • Gas Exchange and Respiration: Mechanisms of gaseous exchange via body surfaces, gills, tracheae, and lungs, ensuring oxygen uptake and carbon dioxide elimination.
            • Circulation Systems: Open and closed circulatory systems, single and double circulation loops, and the structure and function of vertebrate hearts and blood vessels.
            • Excretion and Osmoregulation: Removal of nitrogenous metabolic wastes (ammonia, urea, uric acid) and regulation of water and electrolyte balance via nephrons and excretory organs.
            • Homeostasis and Regulation: Maintenance of a stable internal environment through thermoregulation, osmoregulation, and blood sugar regulation coordinated by nervous and endocrine feedback loops.
            • Renowned Zoologists in Ethiopia: Contributions of Ethiopian scientists and researchers to zoological studies, biodiversity conservation, and animal physiology.
            """.trimIndent()
        )

        // Unit 3
        addNote(
            "Unit 3 - Enzymes",
            "Properties, Structure, Regulation, Kinetics, and Industrial Applications of Enzymes (Sections 3.1 - 3.11)",
            """
            • Nature and Properties of Enzymes: Biological catalysts (mostly globular proteins) that accelerate metabolic reactions by lowering activation energy without being consumed in the reaction.
            • Protein Structure and Enzyme-Substrate Models: Primary, secondary, tertiary, and quaternary protein structures; lock-and-key model and induced-fit model explaining enzyme-substrate binding and specificity.
            • Enzyme Regulation and Classification: Regulation via activators, competitive and non-competitive inhibitors, allosteric control, and structural/functional classification (oxidoreductases, transferases, hydrolases, lyases, isomerases, ligases).
            • Factors Affecting Enzyme Action and Kinetics: Effects of temperature, pH, enzyme concentration, and substrate concentration on reaction velocity (V_max, K_m).
            • Applications and Traditional Malting: Industrial uses of enzymes in textiles, food processing, brewing, and traditional Ethiopian malting practices for local alcohol production.
            • Renowned Biochemists in Ethiopia: Contributions of Ethiopian biochemists to enzymology and scientific research.
            """.trimIndent()
        )

        // Unit 4
        addNote(
            "Unit 4 - Genetics",
            "Genetic Materials, DNA/RNA Structure, Cell Division, and Protein Synthesis (Sections 4.1 - 4.4)",
            """
            • Genetic Materials: Discovery and verification of DNA as the hereditary material through historical experiments (Griffith, Avery-MacLeod-McCarty, Hershey-Chase).
            • Structure and Function of DNA and RNA:
              - DNA Structure: Double helix model (Watson and Crick), nucleotide building blocks (deoxyribose sugar, phosphate group, nitrogenous bases: A, T, C, G), complementary base pairing (A-T, C-G), and semi-conservative DNA replication.
              - RNA Structure: Single-stranded nucleic acids containing ribose and uracil (mRNA, tRNA, rRNA).
            • Cell Division: Mitosis (somatic cell division ensuring genetic continuity) and meiosis (germ cell division producing haploid gametes through genetic recombination and crossing over).
            • Protein Synthesis (Gene Expression): Transcription (synthesizing mRNA from DNA templates in the nucleus) and Translation (ribosomal decoding of mRNA codons into polypeptide chains using tRNA anticodons).
            """.trimIndent()
        )

        // Unit 5
        addNote(
            "Unit 5 - Human Biology and Health",
            "Skeletal System, Reproductive System, Contraception, and Health (Sections 5.1 - 5.5)",
            """
            • Human Skeletal System: Axial and appendicular skeletons, bone structure, and articulations (joints).
            • Human Reproductive System: Male and female reproductive anatomies, gametogenesis (spermatogenesis and oogenesis), hormonal control of the menstrual cycle (positive and negative feedback mechanisms), fertilization, and pregnancy.
            • Contraception and Infertility: Mechanisms of action of hormonal, barrier, and surgical contraceptives; causes and treatments of human infertility.
            • Family Planning: Risks related to lack of family planning, family planning actions, and community health services.
            • Effects of Substance Use: Impact of alcohol consumption, chewing Khat, cannabis, and other drug uses on sexually transmitted infections (STIs) transmission and unwanted pregnancies.
            """.trimIndent()
        )

        // Unit 6
        addNote(
            "Unit 6 - Population and Natural Resources",
            "Population Dynamics, Natural Resources, Environmental Impacts, and Conservation (Sections 6.1 - 6.6)",
            """
            • Population Dynamics: Population size, density, dispersion patterns, exponential versus logistic population growth curves, demographic age structures, and population regulation factors.
            • Natural Resources: Classification into renewable and non-renewable resources, and strategies for conservation of natural resources in Ethiopia.
            • Environmental Impacts: Impacts of traffic accidents on wild and domestic animals, human activities on ecosystems, climate change, global warming, ozone layer depletion, acid rain, loss of biodiversity, toxic bioaccumulation, and resource depletion.
            • Indigenous Conservation Practices: Traditional Ethiopian community-based environmental conservation and resource management systems.
            """.trimIndent()
        )

        return notesList
    }
}