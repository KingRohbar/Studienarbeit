FORM init,
	"Execute only in cloud and in case restricted mode is active
	IF /smb/cl_sscui_restricted_mode=>get_restricted_mode( ) = abap_true.
		TRY.
			DATA(test)   = |Hallo|.
			DATA(test_2) = `Hallo`.
			"Get global instance of Web GUI
			go_fti_webgui_sscui = /fti/cl_webgui_sscui=>create_instance(
					iv_activity_id   = 'SIMG_CFMENUOLMROMRG'
					iv_object_name   = x_header-viewname
					iv_cust_obj_type = vim_view
			).
			"Check whether content change is allowed for current Web GUI
			IF go_fti_webgui_sscui->is_obj_edit_allowed(
					iv_object_id         = x_header-viewname
					iv_object_type       = vim_view
			) = abap_false.

				view_action = anzeigen.
			ENDIF.
		CATCH /fti/cx_webgui_error INTO DATA(lx_webgui_error).
				"Do something here
		ENDTRY.
	ENDIF.
ENDFORM.