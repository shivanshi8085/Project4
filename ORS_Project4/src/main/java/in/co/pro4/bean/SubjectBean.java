package in.co.pro4.bean;

/**
 * Subject JavaBean encapsulates Subject attributes
 * @author Shivanshi Gupta
 *
 */
public class SubjectBean extends BaseBean {
	
	/**
     * Name of Subject
     */
	private String subjectName;
	
	/**
     * Description of Subject
     */
	private String description;
	
	/**
     * CourseId of Subject
     */
	private long courseId;
	
	/**
     * CourseName of Subject
     */
	private String courseName;

	
	/**
     * accessor
     */
	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getCourseId() {
		return courseId;
	}

	public void setCourseId(long courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	@Override
	public String getKey() {
		return id + "";
	}

	@Override
	public String getValue() {
		return subjectName;
	}

}
